package com.example.practicaJMSjms.DataBase;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sensor {
    @Id
    @GeneratedValue
    private int IdDispositivo;
    private String fechaGeneracion; // formato DD/MM/YYYY HH:mm:ss
    private Number temperatura;
    private Number humedad;

    public Sensor() {
    }

    public Sensor(String fechaGeneracion, Number temperatura, Number humedad) {
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
}
