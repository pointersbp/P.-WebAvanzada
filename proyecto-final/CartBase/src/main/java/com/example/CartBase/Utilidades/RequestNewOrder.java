package com.example.CartBase.Utilidades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestNewOrder {
    @JsonProperty("servicio_id")
    Long servicioId;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;

    //formato es dd/MM/yyyy
    @JsonProperty("due_datetime")
    private String dueDatetime;

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getDueDatetime() {
        return dueDatetime;
    }

    public void setDueDatetime(String dueDatetime) {
        this.dueDatetime = dueDatetime;
    }
}
