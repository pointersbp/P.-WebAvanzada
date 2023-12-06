package com.example.NotiBase.Repositorios;

import com.example.NotiBase.Entidades.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Notification, Long> {
    Notification findByNombre(String nombre);
}
