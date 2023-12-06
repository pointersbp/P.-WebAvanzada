package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
//final
public class PaypalAmount {
    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("value")
    private String value;

    @JsonProperty("breakdown")
    private PaypalAmountBreakdown breakdown;

    public PaypalAmount() {
    }

    public PaypalAmount(String currencyCode, String value) {
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public PaypalAmount(String currencyCode, String value, PaypalAmountBreakdown breakdown) {
        this.currencyCode = currencyCode;
        this.value = value;
        this.breakdown = breakdown;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PaypalAmountBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(PaypalAmountBreakdown breakdown) {
        this.breakdown = breakdown;
    }
}
