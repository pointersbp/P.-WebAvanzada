package com.example.CartBase.Controladores;

import com.example.CartBase.Entidades.Order;
import com.example.CartBase.Entidades.Service;
import com.example.CartBase.Repositorios.OrderRepository;
import com.example.CartBase.Repositorios.ServiceRepository;
import com.example.CartBase.Servicios.NotiService;
import com.example.CartBase.Servicios.PaypalService;
import com.example.CartBase.Utilidades.Notification;
import com.example.CartBase.Utilidades.Paypal.PaypalOrder;
import com.example.CartBase.Utilidades.Paypal.Webhook.PaypalEvent;
import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalOrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas/webhook")
public class WebhookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private ServiceRepository servicioRepo;

    @Autowired
    private OrderRepository ordenRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotiService notificacionService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @PostMapping(value = "/paypal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void webhook(
            @RequestHeader("PAYPAL-AUTH-ALGO") String authAlgo,
            @RequestHeader("PAYPAL-CERT-URL") String certUrl,
            @RequestHeader("PAYPAL-TRANSMISSION-ID") String transmissionId,
            @RequestHeader("PAYPAL-TRANSMISSION-SIG") String transmissionSig,
            @RequestHeader("PAYPAL-TRANSMISSION-TIME") String transmissionTime,
            @RequestBody String data
    ) throws JsonProcessingException {
        if(paypalService.verifyWebhookSignature(
                authAlgo,
                certUrl,
                transmissionId,
                transmissionSig,
                transmissionTime,
                data
        )) {
            PaypalEvent parseData = objectMapper.readValue(data, PaypalEvent.class);
            if(parseData.getEventType().equalsIgnoreCase("CHECKOUT.ORDER.APPROVED")) {
                Map<String, Object> resources = parseData.getResource();
                String order_id = (String) resources.get("id");
                Map<String, Object> purchase_unit = (Map<String, Object>) ((List<Object>) resources.get("purchase_units")).get(0);
                //free things???
                String referenceId = (String) purchase_unit.get("reference_id");
                Date date = null;
                try {
                    date = simpleDateFormat.parse((String)purchase_unit.get("description"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //el custom_id lo cojo para poner el usuario
                String user_id = (String) purchase_unit.get("custom_id");
                //lista de servicios
                List<Long> serviciosId = new ArrayList<>();
                for (Map<String, Object> item :
                        (List<Map<String, Object>>) purchase_unit.get("items")) {
                    serviciosId.add(Long.valueOf((String) item.get("sku")));
                }
                List<Service> servicios = servicioRepo.findAllById(serviciosId);

                BigDecimal total = servicios.stream().map(x -> x.getCosto()).reduce(BigDecimal.ZERO, BigDecimal::add);

                Order orden = new Order(
                        order_id,
                        date,
                        user_id,
                        servicios.get(0),
                        total
                );

                PaypalOrder postOrder = paypalService.captureOrderPayment(order_id);
                if(postOrder.getStatus().equals(PaypalOrderStatus.COMPLETED)) {
                    orden = ordenRepo.save(orden);
                    if(orden.getId()!=null) {
                        notificacionService.makeRequest("/api/auth/bot/orderReceived",
                                HttpMethod.POST, user_id, String.class);
                        notificacionService.makeRequest("/notificacion/guardar",
                                HttpMethod.POST, new Notification(0, "" + order_id,
                                        SecurityContextHolder.getContext().getAuthentication().getName(),
                                        new Date(), servicios.get(0).getNombre(), "Paquete aprovisionado"),Void.class);
                        LOGGER.info("Created an order successfully.");
                    }
                } else {
                    LOGGER.warn("An payment capture failed to complete.");
                }
            }
        } else {
            LOGGER.warn("Got an invalid paypal webhook call.");
        }
    }
}
