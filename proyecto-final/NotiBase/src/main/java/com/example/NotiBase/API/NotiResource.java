package com.example.NotiBase.API;

import com.example.NotiBase.Entidades.Notification;
import com.example.NotiBase.Servicios.NotiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notificacion")
@RequiredArgsConstructor
@Slf4j
public class NotiResource {
    private final NotiService notificacionService;

    @GetMapping("/")
    public ResponseEntity<List<Notification>> getNotificaciones() {
        return ResponseEntity.ok().body(notificacionService.getNotificaciones());
    }

    @PostMapping("/guardar")
    public ResponseEntity<Notification> guardarNotificacion(@RequestBody Notification notificacion) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/notificacion/guardar").toUriString());
        return ResponseEntity.created(uri).body(notificacionService.guardarNotificacion(notificacion));
    }

    @PutMapping("/modificar")
    public ResponseEntity<Notification> modificarNotificacion(@RequestBody Notification notificacion) {
        return ResponseEntity.ok().body(notificacionService.modificarNotificacion(notificacion));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarNotificacion(@RequestBody Notification notificacion) {
        return ResponseEntity.ok().body(notificacionService.eliminarNotificacion(notificacion));
    }
}
