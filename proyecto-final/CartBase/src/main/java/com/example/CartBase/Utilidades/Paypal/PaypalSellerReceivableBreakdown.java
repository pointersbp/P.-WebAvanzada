package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalSellerReceivableBreakdown {
    @JsonProperty("gross_amount")
    private PaypalAmount grossAmount;

    @JsonProperty("paypal_fee")
    private PaypalAmount paypalFee;

    @JsonProperty("net_amount")
    private PaypalAmount netAmount;

    public PaypalAmount getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(PaypalAmount grossAmount) {
        this.grossAmount = grossAmount;
    }

    public PaypalAmount getPaypalFee() {
        return paypalFee;
    }

    public void setPaypalFee(PaypalAmount paypalFee) {
        this.paypalFee = paypalFee;
    }

    public PaypalAmount getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(PaypalAmount netAmount) {
        this.netAmount = netAmount;
    }
}
