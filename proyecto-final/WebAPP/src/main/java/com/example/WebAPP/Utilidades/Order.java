package com.example.WebAPP.Utilidades;

import com.example.WebAPP.Utilidades.Enumerate.StatusOrder;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;
    private String paypal_order_id;
    private StatusOrder status;
    private String clientUsername;
    private Date createDate;
    private Date updateDate;
    private Date dueDate;
    private Service servicio;
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypal_order_id() {
        return paypal_order_id;
    }

    public void setPaypal_order_id(String paypal_order_id) {
        this.paypal_order_id = paypal_order_id;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public Service getServicio() {
        return servicio;
    }

    public void setServicio(Service servicio) {
        this.servicio = servicio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
