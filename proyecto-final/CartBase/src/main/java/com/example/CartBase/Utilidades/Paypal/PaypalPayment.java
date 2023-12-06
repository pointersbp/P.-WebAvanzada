package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaypalPayment {
    @JsonProperty("captures")
    private List<PaypalPaymentCapture> captures;

    public List<PaypalPaymentCapture> getCaptures() {
        return captures;
    }

    public void setCaptures(List<PaypalPaymentCapture> captures) {
        this.captures = captures;
    }
}
