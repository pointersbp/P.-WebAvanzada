package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaypalPurchaseUnitItem {
    @JsonProperty("sku")
    private String sku;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("unit_amount")
    private PaypalAmount unitAmount;

    @JsonProperty("description")
    private String description;

    public PaypalPurchaseUnitItem() {
    }

    public PaypalPurchaseUnitItem(String sku, String name, String quantity, PaypalAmount unitAmount, String description) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.unitAmount = unitAmount;
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public PaypalAmount getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(PaypalAmount unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
