package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//final??
public class PaypalPurchaseUnit {
    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("amount")
    private PaypalAmount amount;

    @JsonProperty("payee")
    private PaypalPayee payee;

    @JsonProperty("description")
    private String description;

    @JsonProperty("custom_id")
    private String custom_id;

    @JsonProperty("soft_descriptor")
    private String softDescriptor;

    @JsonProperty("shipping")
    private PaypalShipping shipping;

    @JsonProperty("payments")
    private PaypalPayment payments;

    @JsonProperty("items")
    private List<PaypalPurchaseUnitItem> items;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public PaypalAmount getAmount() {
        return amount;
    }

    public void setAmount(PaypalAmount amount) {
        this.amount = amount;
    }

    public PaypalPayee getPayee() {
        return payee;
    }

    public void setPayee(PaypalPayee payee) {
        this.payee = payee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }

    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
    }

    public PaypalShipping getShipping() {
        return shipping;
    }

    public void setShipping(PaypalShipping shipping) {
        this.shipping = shipping;
    }

    public PaypalPayment getPayments() {
        return payments;
    }

    public void setPayments(PaypalPayment payments) {
        this.payments = payments;
    }

    public List<PaypalPurchaseUnitItem> getItems() {
        return items;
    }

    public void setItems(List<PaypalPurchaseUnitItem> items) {
        this.items = items;
    }
}
