package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalAddress {
    @JsonProperty("address_line_1")
    private String addressLineFirst;

    @JsonProperty("address_line_2")
    private String addressLineSecond;

    @JsonProperty("admin_area_1")
    private String adminAreaOne;

    @JsonProperty("admin_area_2")
    private String adminAreaSecond;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country_code")
    private String countryCode;

    public String getAddressLineFirst() {
        return addressLineFirst;
    }

    public void setAddressLineFirst(String addressLineFirst) {
        this.addressLineFirst = addressLineFirst;
    }

    public String getAddressLineSecond() {
        return addressLineSecond;
    }

    public void setAddressLineSecond(String addressLineSecond) {
        this.addressLineSecond = addressLineSecond;
    }

    public String getAdminAreaOne() {
        return adminAreaOne;
    }

    public void setAdminAreaOne(String adminAreaOne) {
        this.adminAreaOne = adminAreaOne;
    }

    public String getAdminAreaSecond() {
        return adminAreaSecond;
    }

    public void setAdminAreaSecond(String adminAreaSecond) {
        this.adminAreaSecond = adminAreaSecond;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
