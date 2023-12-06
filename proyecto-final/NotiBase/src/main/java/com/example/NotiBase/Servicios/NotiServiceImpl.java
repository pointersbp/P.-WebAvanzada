package com.example.NotiBase.Servicios;

import com.example.NotiBase.Entidades.Notification;
import com.example.NotiBase.Repositorios.NotiRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotiServiceImpl implements NotiService{
    private final NotiRepository notificacionRepo;
    @Override
    public Notification guardarNotificacion(Notification notificacion) {
        return notificacionRepo.save(notificacion);
    }

    @Override
    public Notification modificarNotificacion(Notification notificacion) {
        Notification buscar = notificacionRepo.findByNombre(notificacion.getNombre());
        if (buscar != null) {
            buscar.setUsername(notificacion.getUsername());
            buscar.setPaquete(notificacion.getPaquete());
            buscar.setFecha(notificacion.getFecha());
            buscar.setMensaje(notificacion.getMensaje());
            buscar = notificacionRepo.save(buscar);
        }
        return buscar;
    }

    @Override
    public boolean eliminarNotificacion(Notification notificacion) {
        Notification eliminar = notificacionRepo.findByNombre(notificacion.getNombre());
        if (eliminar != null) {
            notificacionRepo.delete(eliminar);
            return true;
        }
        return false;
    }

    @Override
    public List<Notification> getNotificaciones() {
        return notificacionRepo.findAll();
    }

}
