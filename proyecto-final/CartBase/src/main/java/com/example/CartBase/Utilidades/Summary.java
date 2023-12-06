package com.example.CartBase.Utilidades;

import com.example.CartBase.Entidades.Service;

public class Summary {
    private Service servicio;
    private String fecha;

    public Summary(Service servicio, String fecha) {
        this.servicio = servicio;
        this.fecha = fecha;
    }

    public Service getServicio() {
        return servicio;
    }

    public void setServicio(Service servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
