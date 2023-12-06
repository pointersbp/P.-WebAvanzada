package com.example.UserBase.Servicios;

import com.example.UserBase.Entidades.Rol;
import com.example.UserBase.Entidades.User;
import com.example.UserBase.Repositorios.RoleRepository;
import com.example.UserBase.Repositorios.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository usuarioRepo;
    private final RoleRepository rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailSenderService;
    @Value("${proyecto.webapp.url}")
    private String weburl;

    /**
     * Control REST de usuarios
     * Parte manejada por los administradores.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = usuarioRepo.findByUsername(username);
        if (usuario == null) {
            log.error("Usuario no encontrado en la base de datos");
            throw new UsernameNotFoundException("Usuario no encontrado en la base de datos");
        } else {
            log.info("Usuario encontrado en la base de datos: {}", username);
        }
        Collection<SimpleGrantedAuthority> autoridades = new ArrayList<>();
        usuario.getRoles().forEach(rol -> {autoridades.add(new SimpleGrantedAuthority(rol.getNombre()));});
        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), autoridades);
    }

    @Override
    public User guardarUsuario(User usuario) {
        if (getUsuario(usuario.getUsername()) != null) {
            return null;
        }
        usuario.setRoles(new ArrayList<>());
        log.info("Guardando nuevo usuario {} a la base de datos.", usuario.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepo.save(usuario);
    }

    @Override
    public User crearUsuario(String username, String nombre, String apellido, String correo, String clave, String rol) {
        if (getUsuario(username) != null) {
            return null;
        }
        User usuario = new User(null, nombre, apellido, correo, username,
                passwordEncoder.encode(clave), new ArrayList<>());
        usuarioRepo.save(usuario);
        if (rol.equals("cliente")) {
            addRolAUsuario(username, "ROL_CLIENTE");
        } else if (rol.equals("empleado")) {
            addRolAUsuario(username, "ROL_EMPLEADO");
        } else if (rol.equals("admin")) {
            addRolAUsuario(username, "ROL_ADMIN");
        }
        addRolAUsuario(username, "ROL_USUARIO");
        return getUsuario(username);
    }

    @Override
    public User modificarUsuario(User usuario) {
        if (usuario.getUsername() != null) {
            User modificar = getUsuario(usuario.getUsername());

            // Cosas modificables en esta entidad.
            modificar.setNombre(usuario.getNombre());
            modificar.setApellido(usuario.getApellido());
            modificar.setEmail(usuario.getEmail());
            return usuarioRepo.save(modificar);
        }

        return null;
    }

    @Override
    public User cambiarUsuario(String username, String nombre, String apellido, String correo, String clave, String rol) {
        User modificar = getUsuario(username);
        if (modificar == null) {
            return null;
        }
        modificar.setNombre(nombre);
        modificar.setApellido(apellido);
        modificar.setRoles(new ArrayList<>());
        modificar.setPassword(passwordEncoder.encode(clave));
        usuarioRepo.save(modificar);
        if (rol.equals("cliente")) {
            addRolAUsuario(username, "ROL_CLIENTE");
        } else if (rol.equals("empleado")) {
            addRolAUsuario(username, "ROL_EMPLEADO");
        } else if (rol.equals("admin")) {
            addRolAUsuario(username, "ROL_ADMIN");
        }
        addRolAUsuario(username, "ROL_USUARIO");
        return getUsuario(username);
    }

    @Override
    public boolean eliminarUsuario(User usuario) {
        Authentication sc = SecurityContextHolder.getContext().getAuthentication();
        if (usuario.getUsername() != null) {
            User eliminar = getUsuario(usuario.getUsername());
            if (eliminar != null) {
                boolean eliminable = !eliminar.getRoles()
                        .stream().anyMatch(x -> x.getNombre().equals("ROL_SUPER_ADMIN")) &&
                        !sc.getName().equals(eliminar.getUsername()); // que no estemos eliminando nuestro propio usuario
                if (eliminable) {
                    usuarioRepo.delete(eliminar);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User cambiarClave(User usuario) {
        if (usuario.getUsername() != null) {
            User modificar = getUsuario(usuario.getUsername());
            // Nueva contraseña
            modificar.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepo.save(modificar);
        }
        return null;
    }

    /**
     * Parte del perfil
     * Esta parte puede accederse desde cualquier usuario
     * se valida que el usuario sólo pueda modificarse a sí mismo.
     */

    @Override
    public User perfilUsuarioModificar(User usuario) {
        if (verificarIdentidad(usuario)) {
            return modificarUsuario(usuario);
        }
        return null;
    }

    @Override
    public User perfilUsuarioClave(String clave, String nueva) {
        Authentication sc = SecurityContextHolder.getContext().getAuthentication();
        User usuario = usuarioRepo.findByUsername(sc.getName());
        if (passwordEncoder.matches(clave, usuario.getPassword())) {
            usuario.setPassword(nueva);
            return cambiarClave(usuario);
        }
        return null;
    }

    @Override
    public User obtenerMiPerfil() {
        Authentication sc = SecurityContextHolder.getContext().getAuthentication();
        if (sc != null) {
            String username = sc.getName();
            return usuarioRepo.findByUsername(username);
        }
        return null;
    }
    public boolean verificarIdentidad(User usuario) {
        // Obtener nombre de usuario actual.
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        // El username actual debe ser
        return principal.getName().equals(usuario.getUsername());
    }

    /**
     * Roles
     * Solo los administradores pueden asignar roles.
     * Admin, Clientes, Empleados.
     */
    @Override
    public Rol guardarRol(Rol rol) {
        if (rolRepo.findByNombre(rol.getNombre()) != null) {
            return null;
        }

        log.info("Guardando nuevo rol {} a la base de datos.", rol.getNombre());
        return rolRepo.save(rol);
    }

    @Override
    public void addRolAUsuario(String username, String nombrerol) {
        User usuario = usuarioRepo.findByUsername(username);
        // Verificar que el usuario no tiene el rol.
        if (!usuario.getRoles().stream().anyMatch(x -> x.getNombre().equals(nombrerol))) {
            log.info("Agregando rol {} a usuario {}", nombrerol, username);
            Rol rol = rolRepo.findByNombre(nombrerol);
            usuario.getRoles().add(rol);
        }
    }

    @Override
    public void deleteRolDeUsuario(String username, String nombrerol) {
        User usuario = usuarioRepo.findByUsername(username);
        Rol rol = rolRepo.findByNombre(nombrerol);
        if (usuario != null && rol != null) {
            // El super_admin no puede ser despojado de sus derechos
            if (!usuario.getRoles().stream().anyMatch(x -> x.getNombre().equals("ROL_SUPER_ADMIN"))) {
                usuario.getRoles().removeIf(x -> x.equals(nombrerol));
            }
        }
    }

    /**
     * Consulta de datos de los usuarios.
     * solo los administradores tienen acceso a esto.
     */
    @Override
    public User getUsuario(String username) {
        log.info("Buscando usuario {}", username);
        return usuarioRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsuarios() {
        log.info("Buscando todos los usuarios.");
        return usuarioRepo.findAll();
    }

    @Override
    public List<Rol> getRoles() {
        return rolRepo.findAll();
    }

    @Override
    public boolean registrarCliente(User usuario) {
        User cliente = guardarUsuario(usuario);
        addRolAUsuario(cliente.getUsername(), "ROL_CLIENTE");
        addRolAUsuario(cliente.getUsername(), "ROL_USUARIO");
        emailSenderService.sendSimpleEmail(usuario.getEmail(),
                "Muchas gracias por registrarte en nuestra página.\nEsperamos que disfrutes de nuestros servicios." +
                        "\n\nVisitanos en: " + weburl,
                "Eventos Santiago");
        return true;
    }

    @Override
    public boolean existeUsuario(String username) {
        return usuarioRepo.findByUsername(username) != null;
    }

    @Override
    public String notificarEmpleados(String data) {
        List<User> usuarios = getUsuarios();
        for (User usuario : usuarios) {
            if (usuario.getRoles()
                    .stream().anyMatch(x -> x.getNombre().equals("ROL_EMPLEADO"))) {
                emailSenderService.sendSimpleEmail(usuario.getEmail(), "Un paquete ha sido aprovisionado, debe asignarse un trabajo.",
                        "Eventos Santiago");
            }
        }
        // Luego enviarle correo al cliente.
        User cliente = getUsuario(data);
        if (cliente != null) {
            if (cliente.getEmail() != null) {
                emailSenderService.sendSimpleEmail(cliente.getEmail(), "Su paquete ha sido aprovisionado con éxito, gracias por preferirnos.",
                        "Eventos Santiago");
            }
        }

        return "Correos Enviados";
    }
}
