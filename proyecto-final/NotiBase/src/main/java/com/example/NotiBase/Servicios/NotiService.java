package com.example.NotiBase.Servicios;

import com.example.NotiBase.Entidades.Notification;

import java.util.List;

public interface NotiService {
    Notification guardarNotificacion (Notification notificacion);
    Notification modificarNotificacion (Notification notificacion);
    boolean eliminarNotificacion (Notification notificacion);
    List<Notification> getNotificaciones();
}
