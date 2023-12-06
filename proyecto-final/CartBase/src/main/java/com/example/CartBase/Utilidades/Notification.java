package com.example.CartBase.Utilidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private long id;
    private String nombre;
    private String username;
    private Date fecha;
    private String paquete;
    private String mensaje;
}
