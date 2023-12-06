package com.example.CartBase.Utilidades.Paypal;

import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalOrderIntent;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaypalOrderRequest {
    @JsonProperty("intent")
    private PaypalOrderIntent intent;

    @JsonProperty("purchase_units")
    private List<PaypalPurchaseUnitRequest> purchaseUnits;

    @JsonProperty("application_context")
    private PaypalApplicationContext applicationContext;

    public PaypalOrderRequest() {
    }

    public PaypalOrderRequest(PaypalOrderIntent intent, List<PaypalPurchaseUnitRequest> purchaseUnits, PaypalApplicationContext applicationContext) {
        this.intent = intent;
        this.purchaseUnits = purchaseUnits;
        this.applicationContext = applicationContext;
    }

    public PaypalOrderIntent getIntent() {
        return intent;
    }

    public void setIntent(PaypalOrderIntent intent) {
        this.intent = intent;
    }

    public List<PaypalPurchaseUnitRequest> getPurchaseUnits() {
        return purchaseUnits;
    }

    public void setPurchaseUnits(List<PaypalPurchaseUnitRequest> purchaseUnits) {
        this.purchaseUnits = purchaseUnits;
    }

    public PaypalApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(PaypalApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
