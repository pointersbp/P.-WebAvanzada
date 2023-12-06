package com.example.CartBase.Utilidades;

import java.util.Date;

public class Resume {
    private int cantCompras;
    private int cantPendientes;
    private int cantCompletados;
    private Date fecha;

    public Resume() {
    }

    public Resume(int cantCompras, int cantPendientes, int cantCompletados, Date fecha) {
        this.cantCompras = cantCompras;
        this.cantPendientes = cantPendientes;
        this.cantCompletados = cantCompletados;
        this.fecha = fecha;
    }

    public int getCantCompras() {
        return cantCompras;
    }

    public void setCantCompras(int cantCompras) {
        this.cantCompras = cantCompras;
    }

    public int getCantPendientes() {
        return cantPendientes;
    }

    public void setCantPendientes(int cantPendientes) {
        this.cantPendientes = cantPendientes;
    }

    public int getCantCompletados() {
        return cantCompletados;
    }

    public void setCantCompletados(int cantCompletados) {
        this.cantCompletados = cantCompletados;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
