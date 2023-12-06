package com.example.CartBase.Utilidades.Paypal;

import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalHttpMethod;
import com.fasterxml.jackson.annotation.JsonProperty;

//final, no utilizado
public class PaypalLinkDescription {
    @JsonProperty("href")
    private String href;

    @JsonProperty("rel")
    private String rel;

    @JsonProperty("method")
    private PaypalHttpMethod method;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public PaypalHttpMethod getMethod() {
        return method;
    }

    public void setMethod(PaypalHttpMethod method) {
        this.method = method;
    }
}
