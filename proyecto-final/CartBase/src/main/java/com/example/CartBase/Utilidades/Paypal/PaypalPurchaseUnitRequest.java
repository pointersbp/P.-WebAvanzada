package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaypalPurchaseUnitRequest {
    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private PaypalAmount amout;

    @JsonProperty("custom_id")
    private String customId;

    @JsonProperty("items")
    private List<PaypalPurchaseUnitItem> items;

    public PaypalPurchaseUnitRequest() {
    }

    public PaypalPurchaseUnitRequest(String referenceId, String description, PaypalAmount amout, String customId, List<PaypalPurchaseUnitItem> items) {
        this.referenceId = referenceId;
        this.description = description;
        this.amout = amout;
        this.customId = customId;
        this.items = items;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaypalAmount getAmout() {
        return amout;
    }

    public void setAmout(PaypalAmount amout) {
        this.amout = amout;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public List<PaypalPurchaseUnitItem> getItems() {
        return items;
    }

    public void setItems(List<PaypalPurchaseUnitItem> items) {
        this.items = items;
    }
}
