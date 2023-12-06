package com.example.CartBase.Utilidades.Paypal;

import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalOrderIntent;
import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalOrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
public class PaypalOrder {
    @JsonProperty("id")
    private String id;

    @JsonProperty("intent")
    private PaypalOrderIntent intent;

    @JsonProperty("status")
    private PaypalOrderStatus status;

    @JsonProperty("purchase_units")
    private List<PaypalPurchaseUnit> purchaseUnits;

    @JsonProperty("payer")
    private PaypalPayer payer;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("links")
    private List<PaypalLinkDescription> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaypalOrderIntent getIntent() {
        return intent;
    }

    public void setIntent(PaypalOrderIntent intent) {
        this.intent = intent;
    }

    public PaypalOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PaypalOrderStatus status) {
        this.status = status;
    }

    public List<PaypalPurchaseUnit> getPurchaseUnits() {
        return purchaseUnits;
    }

    public void setPurchaseUnits(List<PaypalPurchaseUnit> purchaseUnits) {
        this.purchaseUnits = purchaseUnits;
    }

    public PaypalPayer getPayer() {
        return payer;
    }

    public void setPayer(PaypalPayer payer) {
        this.payer = payer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<PaypalLinkDescription> getLinks() {
        return links;
    }

    public void setLinks(List<PaypalLinkDescription> links) {
        this.links = links;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
