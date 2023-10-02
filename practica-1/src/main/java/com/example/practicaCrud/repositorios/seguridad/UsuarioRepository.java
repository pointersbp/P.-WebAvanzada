package com.example.practicaCrud.repositorios.seguridad;

import com.example.practicaCrud.entidades.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByUsername(String username);
}
