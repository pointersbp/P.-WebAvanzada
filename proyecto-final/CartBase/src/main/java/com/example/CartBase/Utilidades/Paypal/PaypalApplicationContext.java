package com.example.CartBase.Utilidades.Paypal;

import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalLandingPage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalApplicationContext {
    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("landing_page")
    private PaypalLandingPage landingPage;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancel_url;

    public PaypalApplicationContext() {
    }

    public PaypalApplicationContext(String brandName, PaypalLandingPage landingPage, String returnUrl, String cancel_url) {
        this.brandName = brandName;
        this.landingPage = landingPage;
        this.returnUrl = returnUrl;
        this.cancel_url = cancel_url;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public PaypalLandingPage getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(PaypalLandingPage landingPage) {
        this.landingPage = landingPage;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }
}
