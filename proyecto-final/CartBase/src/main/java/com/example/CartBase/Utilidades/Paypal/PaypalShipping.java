package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalShipping {
    @JsonProperty("name")
    private PaypalShippingName name;

    @JsonProperty("address")
    private PaypalAddress paypalShippingAddress;
}
