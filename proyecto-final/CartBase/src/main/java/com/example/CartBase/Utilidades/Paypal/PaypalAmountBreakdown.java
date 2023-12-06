package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalAmountBreakdown {
    @JsonProperty("item_total")
    private PaypalAmount itemTotal;

    public PaypalAmountBreakdown(String currencyCode, String value) {
        this.itemTotal = new PaypalAmount(currencyCode, value);
    }

    public PaypalAmount getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(PaypalAmount itemTotal) {
        this.itemTotal = itemTotal;
    }
}
