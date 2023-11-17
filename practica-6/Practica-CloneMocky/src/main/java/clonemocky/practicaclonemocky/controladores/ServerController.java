package clonemocky.practicaclonemocky.controladores;

import clonemocky.practicaclonemocky.entidades.MockProject;
import clonemocky.practicaclonemocky.entidades.Rols;
import clonemocky.practicaclonemocky.entidades.User;
import clonemocky.practicaclonemocky.entidades.repositorios.MockProjectRepository;
import clonemocky.practicaclonemocky.entidades.repositorios.UserRepository;
import clonemocky.practicaclonemocky.servicios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(path = "/")
public class ServerController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository usuarioRepository;

    @Autowired
    MockProjectRepository proyectoRepository;

    @Autowired
    private MessageSource messageSource;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @RequestMapping("/")
    public String inicio(Model model) {
        if (authService.isAuthenticated()) {
            model.addAttribute("autenticado", true);
            if (authService.containsRole("ROLE_ADMIN")) {
                model.addAttribute("admin", true);
            }
        }

        return "inicio";
    }

    @RequestMapping("/endpoint")
    public String endpoint(Model model, Locale locale) {
        if (authService.containsRole("ROLE_ADMIN")) {
            model.addAttribute("admin",true );
        }
        // Traduction
        model.addAttribute("proj", messageSource.getMessage("proj", null, locale));
        model.addAttribute("default", messageSource.getMessage("default", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("titulo", null, locale));
        model.addAttribute("ruta", messageSource.getMessage("ruta", null, locale));
        model.addAttribute("verbo", messageSource.getMessage("verbo", null, locale));
        model.addAttribute("codigo", messageSource.getMessage("codigo", null, locale));
        model.addAttribute("c100", messageSource.getMessage("c100", null, locale));
        model.addAttribute("c101", messageSource.getMessage("c101", null, locale));
        model.addAttribute("c102", messageSource.getMessage("c102", null, locale));
        model.addAttribute("c200", messageSource.getMessage("c200", null, locale));
        model.addAttribute("c201", messageSource.getMessage("c201", null, locale));
        model.addAttribute("c202", messageSource.getMessage("c202", null, locale));
        model.addAttribute("c203", messageSource.getMessage("c203", null, locale));
        model.addAttribute("c204", messageSource.getMessage("c204", null, locale));
        model.addAttribute("c205", messageSource.getMessage("c205", null, locale));
        model.addAttribute("c206", messageSource.getMessage("c206", null, locale));
        model.addAttribute("c207", messageSource.getMessage("c207", null, locale));
        model.addAttribute("c208", messageSource.getMessage("c208", null, locale));
        model.addAttribute("c226", messageSource.getMessage("c226", null, locale));
        model.addAttribute("c300", messageSource.getMessage("c300", null, locale));
        model.addAttribute("c301", messageSource.getMessage("c301", null, locale));
        model.addAttribute("c302", messageSource.getMessage("c302", null, locale));
        model.addAttribute("c303", messageSource.getMessage("c303", null, locale));
        model.addAttribute("c304", messageSource.getMessage("c304", null, locale));
        model.addAttribute("c305", messageSource.getMessage("c305", null, locale));
        model.addAttribute("c306", messageSource.getMessage("c306", null, locale));
        model.addAttribute("c307", messageSource.getMessage("c307", null, locale));
        model.addAttribute("c308", messageSource.getMessage("c308", null, locale));
        model.addAttribute("c400", messageSource.getMessage("c400", null, locale));
        model.addAttribute("c401", messageSource.getMessage("c401", null, locale));
        model.addAttribute("c402", messageSource.getMessage("c402", null, locale));
        model.addAttribute("c403", messageSource.getMessage("c403", null, locale));
        model.addAttribute("c404", messageSource.getMessage("c404", null, locale));
        model.addAttribute("c405", messageSource.getMessage("c405", null, locale));
        model.addAttribute("c406", messageSource.getMessage("c406", null, locale));
        model.addAttribute("c407", messageSource.getMessage("c407", null, locale));
        model.addAttribute("c408", messageSource.getMessage("c408", null, locale));
        model.addAttribute("c409", messageSource.getMessage("c409", null, locale));
        model.addAttribute("c410", messageSource.getMessage("c410", null, locale));
        model.addAttribute("c411", messageSource.getMessage("c411", null, locale));
        model.addAttribute("c412", messageSource.getMessage("c412", null, locale));
        model.addAttribute("c413", messageSource.getMessage("c413", null, locale));
        model.addAttribute("c414", messageSource.getMessage("c414", null, locale));
        model.addAttribute("c415", messageSource.getMessage("c415", null, locale));
        model.addAttribute("c416", messageSource.getMessage("c416", null, locale));
        model.addAttribute("c417", messageSource.getMessage("c417", null, locale));
        model.addAttribute("c418", messageSource.getMessage("c418", null, locale));
        model.addAttribute("c419", messageSource.getMessage("c419", null, locale));
        model.addAttribute("c420", messageSource.getMessage("c420", null, locale));
        model.addAttribute("c422", messageSource.getMessage("c422", null, locale));
        model.addAttribute("c423", messageSource.getMessage("c423", null, locale));
        model.addAttribute("c424", messageSource.getMessage("c424", null, locale));
        model.addAttribute("c424a", messageSource.getMessage("c424a", null, locale));
        model.addAttribute("c425", messageSource.getMessage("c425", null, locale));
        model.addAttribute("c426", messageSource.getMessage("c426", null, locale));
        model.addAttribute("c428", messageSource.getMessage("c428", null, locale));
        model.addAttribute("c429", messageSource.getMessage("c429", null, locale));
        model.addAttribute("c431", messageSource.getMessage("c431", null, locale));
        model.addAttribute("c444", messageSource.getMessage("c444", null, locale));
        model.addAttribute("c449", messageSource.getMessage("c449", null, locale));
        model.addAttribute("c450", messageSource.getMessage("c450", null, locale));
        model.addAttribute("c451", messageSource.getMessage("c451", null, locale));
        model.addAttribute("c451a", messageSource.getMessage("c451a", null, locale));
        model.addAttribute("c494", messageSource.getMessage("c494", null, locale));
        model.addAttribute("c495", messageSource.getMessage("c495", null, locale));
        model.addAttribute("c496", messageSource.getMessage("c496", null, locale));
        model.addAttribute("c497", messageSource.getMessage("c497", null, locale));
        model.addAttribute("c499", messageSource.getMessage("c499", null, locale));
        model.addAttribute("c500", messageSource.getMessage("c500", null, locale));
        model.addAttribute("c501", messageSource.getMessage("c501", null, locale));
        model.addAttribute("c502", messageSource.getMessage("c502", null, locale));
        model.addAttribute("c503", messageSource.getMessage("c503", null, locale));
        model.addAttribute("c504", messageSource.getMessage("c504", null, locale));
        model.addAttribute("c505", messageSource.getMessage("c505", null, locale));
        model.addAttribute("c506", messageSource.getMessage("c506", null, locale));
        model.addAttribute("c507", messageSource.getMessage("c507", null, locale));
        model.addAttribute("c508", messageSource.getMessage("c508", null, locale));
        model.addAttribute("c509", messageSource.getMessage("c509", null, locale));
        model.addAttribute("c510", messageSource.getMessage("c510", null, locale));
        model.addAttribute("c511", messageSource.getMessage("c511", null, locale));
        model.addAttribute("tipo", messageSource.getMessage("tipo", null, locale));
        model.addAttribute("tiempo", messageSource.getMessage("tiempo", null, locale));
        model.addAttribute("ano", messageSource.getMessage("ano", null, locale));
        model.addAttribute("mes", messageSource.getMessage("mes", null, locale));
        model.addAttribute("semana", messageSource.getMessage("semana", null, locale));
        model.addAttribute("dia", messageSource.getMessage("dia", null, locale));
        model.addAttribute("hora", messageSource.getMessage("hora", null, locale));
        model.addAttribute("nombre", messageSource.getMessage("nombre", null, locale));
        model.addAttribute("descripcion", messageSource.getMessage("descripcion", null, locale));
        model.addAttribute("jwt", messageSource.getMessage("jwt", null, locale));
        model.addAttribute("demora", messageSource.getMessage("demora", null, locale));

        model.addAttribute("proyectos", proyectoRepository.findAllByUsuarioId(
                authService.getUserAuthenticated().getUsername()));
        return "endpoint";
    }

    @RequestMapping("/login")
    public String login() {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("misUsuarios",usuarioRepository.findAll());
        model.addAttribute("usernameActual", authService.getUserAuthenticated().getUsername());
        model.addAttribute("misUsuarios",usuarioRepository.findAll());
        return "admin";
    }

    @RequestMapping("/crearusuario")
    public String crearUsuario(@RequestParam String username,
                               @RequestParam String nombre,
                               @RequestParam String rol,
                               @RequestParam String password) {
        Rols uRol = new Rols(rol);;
        User nuevo = new User();
        if (rol.equalsIgnoreCase("ROLE_ADMIN")) {
            nuevo.setRoles(new HashSet<>(Arrays.asList(uRol,new Rols("ROLE_USER"))));
        }  else {
            nuevo.setRoles(new HashSet<>(Arrays.asList(uRol)));
        }
        nuevo.setRol(rol);
        nuevo.setUsername(username);
        nuevo.setPassword(bCryptPasswordEncoder.encode(password));
        nuevo.setNombre(nombre);
        nuevo.setActivo(true);
        // saved
        usuarioRepository.save(nuevo);
        return "redirect:/admin";
    }
    @RequestMapping("/modificarusuario")
    public String modificarUsuario(@RequestParam String username,
                                   @RequestParam String nombre,
                                   @RequestParam String rol,
                                   @RequestParam String password) {
        User usuario = usuarioRepository.findByUsername(username);
        Rols uRol;
        User admin = authService.getUserAuthenticated();
        boolean noPuedeModificar = !admin.getUsername().equals("admin") &&
                usuario.getRol().equalsIgnoreCase("ROLE_ADMIN") &&
                usuario.getUsername() != admin.getUsername();
        if (usuario != null && !noPuedeModificar) {
            if (rol.equalsIgnoreCase("ROLE_ADMIN")) {
                uRol = new Rols(rol);
                usuario.setRoles(new HashSet<>(Arrays.asList(uRol,new Rols("ROLE_USER"))));
                usuario.setRol("ROLE_ADMIN");
            }  else {
                uRol = new Rols("ROLE_USER");
                usuario.setRoles(new HashSet<>(Arrays.asList(uRol)));
                usuario.setRol("ROLE_USER");
            }
            usuario.setPassword(bCryptPasswordEncoder.encode(password));
            usuario.setNombre(nombre);
            // updated
            usuarioRepository.save(usuario);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/eliminarusuario")
    public String eliminarUsuario(@RequestParam String username) {
        User admin = authService.getUserAuthenticated();
        if (admin != null) {
            User aEliminar = usuarioRepository.findByUsername(username);
            if (aEliminar != null) {
                if (!admin.getUsername().equals(aEliminar.getUsername()) &&
                        !aEliminar.getUsername().equals("admin")) {

                    List<MockProject> data = proyectoRepository.findAllByUsuarioId(aEliminar.getUsername());
                    for (MockProject proyecto : data) {
                        proyectoRepository.delete(proyecto);
                    }
                    usuarioRepository.delete(aEliminar);
                }
            }
        }
        return "redirect:/admin";
    }

    @RequestMapping("/modificar")
    public String modificar(Model model, @RequestParam String username) {
        User usuario = usuarioRepository.findByUsername(username);
        model.addAttribute("modificar", usuario);
        model.addAttribute("usernameActual", authService.getUserAuthenticated().getUsername());
        model.addAttribute("misUsuarios",usuarioRepository.findAll());
        return "admin";
    }

}
