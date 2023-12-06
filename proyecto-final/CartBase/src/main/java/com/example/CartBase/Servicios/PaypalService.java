package com.example.CartBase.Servicios;

import com.example.CartBase.Utilidades.Paypal.*;
import com.example.CartBase.Utilidades.Paypal.Webhook.PaypalWebhookVerifyRequest;
import com.example.CartBase.Utilidades.Paypal.Webhook.PaypalWebhookVerifyResponse;
import com.example.CartBase.Utilidades.Paypal.Webhook.PaypalWebhookVerifyResponseStatus;
import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalLandingPage;
import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalOrderIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PaypalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaypalService.class);

    private final static String urlToken = "https://api-m.sandbox.paypal.com/v1/oauth2/token?grant_type=client_credentials";

    @Value("${paypal.api.url}")
    private String paypalUrl;

    @Value("${paypal.api.client.id}")
    private String clientId;

    @Value("${paypal.api.client.secret}")
    private String clientSecret;

    @Value("${valores.pesodolar}")
    private BigDecimal radioPesoDolar;

    @Value("${paypal.webhook.id}")
    private String webhookId;

    @Value("${paypal.brand.name}")
    private String brandName;

    private PaypalToken paypalToken;


    private RestTemplate restTemplate;

    @Autowired
    public PaypalService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //endregion

    public PaypalOrder getPaypalOrderData(String id) {
        ResponseEntity<Object> response = makeRequest("/v2/checkout/orders/"+id, HttpMethod.GET, null, PaypalOrder.class);
        return (PaypalOrder) response.getBody();
    }

    private ResponseEntity<Object> makeRequest(String path, HttpMethod method, Object body, Class clase) {
        HttpHeaders headers = new HttpHeaders() {{
            setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            setBearerAuth(paypalToken.getAccessToken());
            setContentType(MediaType.APPLICATION_JSON);
        }};
        HttpEntity<Object> request = null;
        if (body == null) {
            request = new HttpEntity(headers);
        } else {
            request = new HttpEntity(body, headers);
        }
        ResponseEntity<Object> responseEntity = null;
        int retries = 2;
        while (retries-- > 0) {
            try {
                responseEntity = restTemplate.exchange(paypalUrl + path, method, request, clase);
                break;
            } catch (HttpStatusCodeException e) {
                if(e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    renewToken();
                    headers.setBearerAuth(paypalToken.getAccessToken());
                } else {
                    throw e;
                }

            }
        }
        return responseEntity;
    }

    public boolean verifyWebhookSignature(String authAlgo, String certUrl, String transmissionId, String transmissionSig, String transmissionTime, String webhookEvent) {
        PaypalWebhookVerifyRequest requestBody = new PaypalWebhookVerifyRequest(
                authAlgo,
                certUrl,
                transmissionId,
                transmissionSig,
                transmissionTime,
                webhookId,
                webhookEvent
        );

        ResponseEntity<Object> responseEntity = makeRequest(
                "/v1/notifications/verify-webhook-signature",
                HttpMethod.POST,
                requestBody,
                PaypalWebhookVerifyResponse.class
        );

        return ((PaypalWebhookVerifyResponse) responseEntity.getBody()).getVerificationStatus().equals(PaypalWebhookVerifyResponseStatus.SUCCESS);
    }

    public PaypalOrder createOrder(String clientUsername, String returnUrl, String cancelUrl, List<com.example.CartBase.Entidades.Service> servicios, String dueDatetime) {
        BigDecimal total = BigDecimal.ZERO;
        List<PaypalPurchaseUnitItem> items = new ArrayList<>();
        for (com.example.CartBase.Entidades.Service servicio :
                servicios) {
            BigDecimal precio = servicio.getCosto().divide(radioPesoDolar, 2, RoundingMode.DOWN);
            total = total.add(precio);
            items.add(
                    new PaypalPurchaseUnitItem(
                            servicio.getId().toString(),
                            servicio.getNombre(),
                            "1",
                            new PaypalAmount("USD", precio.toString()),
                            ""
                    )
            );
        }
        total.setScale(2, RoundingMode.DOWN);
        String totalStr = total.toString();
        List<PaypalPurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(
                new PaypalPurchaseUnitRequest(
                        "None",
                        dueDatetime,
                        new PaypalAmount(
                                "USD",
                                totalStr,
                                new PaypalAmountBreakdown("USD", totalStr)
                        ),
                        clientUsername,
                        items
                )
        );
        PaypalOrderRequest paypalOrderRequest = new PaypalOrderRequest(
                PaypalOrderIntent.CAPTURE,
                purchaseUnits,
                new PaypalApplicationContext(
                        this.brandName,
                        PaypalLandingPage.BILLING,
                        returnUrl,
                        cancelUrl
                )
        );

        ResponseEntity<Object> responseEntity = makeRequest(
                "/v2/checkout/orders",
                HttpMethod.POST,
                paypalOrderRequest,
                PaypalOrder.class
        );
        return (PaypalOrder) responseEntity.getBody();
    }

    public PaypalOrder captureOrderPayment(String id) {
        ResponseEntity<Object> responseEntity = makeRequest(
                "/v2/checkout/orders/" + id + "/capture",
                HttpMethod.POST,
                null,
                PaypalOrder.class
        );
        if (responseEntity.hasBody()) {
            return (PaypalOrder) responseEntity.getBody();
        }
        return null;
    }

    private void renewToken() {
        HttpHeaders basicAuth = new HttpHeaders() {{
            setBasicAuth(clientId, clientSecret);
            setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }};
        paypalToken = restTemplate.postForObject(urlToken, new HttpEntity<>(basicAuth), PaypalToken.class);
        LOGGER.info("Paypal token renewed.");
    }

    @PostConstruct
    private void executePostConstruct() {
        renewToken();
    }
}
