package com.example.CartBase.Utilidades.Paypal.Webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class PaypalWebhookVerifyRequest {
    @JsonProperty("auth_algo")
    private String authAlgo;

    @JsonProperty("cert_url")
    private String certUrl;

    @JsonProperty("transmission_id")
    private String transmissionId;

    @JsonProperty("transmission_sig")
    private String transmissionSig;

    @JsonProperty("transmission_time")
    private String transmissionTime;

    @JsonProperty("webhook_id")
    private String webhookId;

    @JsonProperty("webhook_event")
    @JsonRawValue
    private String webhookEvent;

    public PaypalWebhookVerifyRequest() {
    }

    public PaypalWebhookVerifyRequest(String authAlgo, String certUrl, String transmissionId, String transmissionSig, String transmissionTime, String webhookId, String webhookEvent) {
        this.authAlgo = authAlgo;
        this.certUrl = certUrl;
        this.transmissionId = transmissionId;
        this.transmissionSig = transmissionSig;
        this.transmissionTime = transmissionTime;
        this.webhookId = webhookId;
        this.webhookEvent = webhookEvent;
    }

    public String getAuthAlgo() {
        return authAlgo;
    }

    public void setAuthAlgo(String authAlgo) {
        this.authAlgo = authAlgo;
    }

    public String getCertUrl() {
        return certUrl;
    }

    public void setCertUrl(String certUrl) {
        this.certUrl = certUrl;
    }

    public String getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(String transmissionId) {
        this.transmissionId = transmissionId;
    }

    public String getTransmissionSig() {
        return transmissionSig;
    }

    public void setTransmissionSig(String transmissionSig) {
        this.transmissionSig = transmissionSig;
    }

    public String getTransmissionTime() {
        return transmissionTime;
    }

    public void setTransmissionTime(String transmissionTime) {
        this.transmissionTime = transmissionTime;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public String getWebhookEvent() {
        return webhookEvent;
    }

    public void setWebhookEvent(String webhookEvent) {
        this.webhookEvent = webhookEvent;
    }
}
