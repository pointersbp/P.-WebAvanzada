package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalPayer {
    @JsonProperty("name")
    private PaypalPayerName name;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("payer_id")
    private String payerId;

    @JsonProperty("address")
    private PaypalAddress address;

    public PaypalPayerName getName() {
        return name;
    }

    public void setName(PaypalPayerName name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public PaypalAddress getAddress() {
        return address;
    }

    public void setAddress(PaypalAddress address) {
        this.address = address;
    }
}
