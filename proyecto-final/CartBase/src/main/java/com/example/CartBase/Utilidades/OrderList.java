package com.example.CartBase.Utilidades;

import com.example.CartBase.Entidades.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderList {
    @JsonProperty("size")
    private int size;
    @JsonProperty("maxPage")
    private int maxPage;
    @JsonProperty("ordenes")
    private List<Order> ordenList;

    public OrderList() {
    }

    public OrderList(int size, int maxPage, List<Order> ordenList) {
        this.size = size;
        this.maxPage = maxPage;
        this.ordenList = ordenList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<Order> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Order> ordenList) {
        this.ordenList = ordenList;
    }
}
