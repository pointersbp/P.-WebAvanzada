package com.example.UserBase.Repositorios;

import com.example.UserBase.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
