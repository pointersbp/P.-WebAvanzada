package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalShippingName {
    @JsonProperty("full_name")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
