package com.example.practicaJMSclient.Entidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Info {
    private int IdDispositivo;
    private String fechaGeneracion;
    private Number temperatura;
    private Number humedad;
    private Number clientesActuales;

    public Info() {
    }

    public Info(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
        this.humedad = 0;
        this.temperatura = 0;
    }

    public Info(Number temperatura, Number humedad) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date = new Date();
        this.fechaGeneracion = format.format(date);
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public Info(int IdDispositivo, Number temperatura, Number humedad) {
        this.IdDispositivo = IdDispositivo;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date = new Date();
        this.fechaGeneracion = format.format(date);
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public Info(int IdDispositivo, Number temperatura, Number humedad, Number clientesActuales) {
        this.IdDispositivo = IdDispositivo;
        this.clientesActuales = clientesActuales;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date = new Date();
        this.fechaGeneracion = format.format(date);
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public Info(String fechaGeneracion, Number temperatura, Number humedad) {
        this.fechaGeneracion = fechaGeneracion;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public int getIdDispositivo() {
        return IdDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Number getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Number temperatura) {
        this.temperatura = temperatura;
    }

    public Number getHumedad() {
        return humedad;
    }

    public void setHumedad(Number humedad) {
        this.humedad = humedad;
    }

    public Number getClientesActuales() {
        return clientesActuales;
    }

    public void setClientesActuales(Number clientesActuales) {
        this.clientesActuales = clientesActuales;
    }

}