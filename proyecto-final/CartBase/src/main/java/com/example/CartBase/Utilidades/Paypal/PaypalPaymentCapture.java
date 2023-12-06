package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaypalPaymentCapture {
    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    private PaypalAmount amount;

    @JsonProperty("final_capture")
    private boolean finalCapture;

    @JsonProperty("seller_protection")
    private PaypalSellerProtection sellerProtection;

    @JsonProperty("seller_receivable_breakdown")
    private PaypalSellerReceivableBreakdown sellerReceivableBreakdown;

    @JsonProperty("links")
    private List<PaypalLinkDescription> links;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("update_time")
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaypalAmount getAmount() {
        return amount;
    }

    public void setAmount(PaypalAmount amount) {
        this.amount = amount;
    }

    public boolean isFinalCapture() {
        return finalCapture;
    }

    public void setFinalCapture(boolean finalCapture) {
        this.finalCapture = finalCapture;
    }

    public PaypalSellerProtection getSellerProtection() {
        return sellerProtection;
    }

    public void setSellerProtection(PaypalSellerProtection sellerProtection) {
        this.sellerProtection = sellerProtection;
    }

    public PaypalSellerReceivableBreakdown getSellerReceivableBreakdown() {
        return sellerReceivableBreakdown;
    }

    public void setSellerReceivableBreakdown(PaypalSellerReceivableBreakdown sellerReceivableBreakdown) {
        this.sellerReceivableBreakdown = sellerReceivableBreakdown;
    }

    public List<PaypalLinkDescription> getLinks() {
        return links;
    }

    public void setLinks(List<PaypalLinkDescription> links) {
        this.links = links;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
