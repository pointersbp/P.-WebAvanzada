package com.example.UserBase.Servicios;

import com.example.UserBase.Entidades.Rol;
import com.example.UserBase.Entidades.User;

import java.util.List;

public interface UserService {
    // Parte del usuario (Super Admin, Admin)
    User guardarUsuario(User usuario);
    User crearUsuario(String username, String nombre, String apellido, String correo, String clave, String rol);
    User modificarUsuario(User usuario);
    User cambiarUsuario(String username, String nombre, String apellido, String correo, String clave, String rol);
    boolean eliminarUsuario(User usuario);
    User cambiarClave(User usuario);
    // Perfil de usuario (Todos los usuarios
    User perfilUsuarioModificar(User usuario);
    User perfilUsuarioClave(String clave, String nueva);
    User obtenerMiPerfil();
    // Roles (Super Admin, Admin)
    Rol guardarRol(Rol rol);
    void addRolAUsuario(String username, String nombrerol);
    void deleteRolDeUsuario(String username, String nombrerol);
    // Consulta de usuarios (Admin)
    User getUsuario(String username);
    List<User> getUsuarios();
    List<Rol> getRoles();
    // Registro
    boolean registrarCliente(User usuario);
    // Validaciones
    boolean existeUsuario(String username);
    // Email
    String notificarEmpleados(String data);
}
