package clonemocky.practicaclonemocky.controladores;

import clonemocky.practicaclonemocky.entidades.MockEndpoint;
import clonemocky.practicaclonemocky.entidades.MockProject;
import clonemocky.practicaclonemocky.entidades.User;
import clonemocky.practicaclonemocky.entidades.composite.MockEndpointPK;
import clonemocky.practicaclonemocky.entidades.repositorios.MockEndpointRepository;
import clonemocky.practicaclonemocky.entidades.repositorios.MockProjectRepository;
import clonemocky.practicaclonemocky.servicios.AuthService;
import clonemocky.practicaclonemocky.utilidades.MockEndpointsMethods;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Controller
@RequestMapping("/proyecto")
public class UserRolController {
    @Autowired
    private MockProjectRepository mockProyectoRepo;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MockEndpointRepository mockEndpointRepo;

    @Autowired
    private AuthService authService;

    @Value("${token_jwt}")
    private String llaveSecreta;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String dashboard(Model model) {
        Iterable<MockProject> proyectos = null;
        if (authService.isAuthenticated()) {
            model.addAttribute("autenticado", true);
            if (authService.containsRole("ROLE_ADMIN")) {
                model.addAttribute("admin", true);
                proyectos = mockProyectoRepo.findAll();
            } else {
                proyectos = mockProyectoRepo.findAllByUsuarioId(authService.getUserAuthenticated().getUsername());
            }
        }
        model.asMap().put("proyectos", proyectos);
        return "test/listaProyecto.html";
    }

    @GetMapping(value = "/new")
    public RedirectView newProyect() {
        MockProject proyecto = new MockProject();
        proyecto.setUsuarioId(authService.getUserAuthenticated().getUsername());
        mockProyectoRepo.save(proyecto);
        return new RedirectView("/proyecto/p/" + proyecto.getId());
    }

    @GetMapping(value = "/delete/{id}")
    public RedirectView deleteproyect(@PathVariable("id") String id) {
        if (authService.containsRole("ROLE_ADMIN") || mockProyectoRepo.existsByIdAndUsuarioId(id, authService.getUserAuthenticated().getUsername())) {
            mockProyectoRepo.deleteById(id);
        }
        return new RedirectView("/proyecto/");
    }

    @GetMapping("/p/{id}")
    public String modify(Model model, @PathVariable("id") String id) {
        MockProject proyecto = mockProyectoRepo.findById(id).orElse(null);
        if (authService.containsRole("ROLE_ADMIN") || (proyecto != null && proyecto.getUsuarioId().equals(authService.getUserAuthenticated().getUsername()))) {
            model.asMap().put("proyecto", proyecto);
            model.addAttribute("admin", authService.containsRole("ROLE_ADMIN"));
            return "test/verProyecto.html";
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "SIN PERMISO PARA VISUALIZAR PROYECTO"
        );

    }

    @GetMapping("/p/{id}/add")
    public String addVisualEndpoint(Model model, Locale locale, @PathVariable("id") String id, @RequestParam(name = "error", required = false) String error) {
        model.asMap().put("id", id);
        if (authService.containsRole("ROLE_ADMIN")) {
            model.addAttribute("admin", true);
        } else if (!mockProyectoRepo.existsByIdAndUsuarioId(id, authService.getUserAuthenticated().getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No tiene permiso para añadir endpoint a este proyecto."
            );
        }
        if(error!=null) {
            model.addAttribute("error", error);
        }

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
        return "endpoint.html";
    }

    @PostMapping("/p/{id}/add")
    public RedirectView addEndpoint(HttpServletRequest request, @PathVariable("id") String id) {
        MockProject proyecto = mockProyectoRepo.findById(id).orElse(null);

        if (proyecto != null) {
            if (!authService.containsRole("ROLE_ADMIN") && !proyecto.getUsuarioId().equals(authService.getUserAuthenticated().getUsername())) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "No tiene permiso para añadir endpoint a este proyecto."
                );
            }

            Map<String, String[]> parameterMap = request.getParameterMap();
            String path = parameterMap.get("ruta")[0];
            if (path.charAt(0) != '/') {
                path = '/' + path;
            }
            MockEndpointPK endpointID = new MockEndpointPK(id, MockEndpointsMethods.valueOf(parameterMap.get("getRadio")[0]), path);
            if (mockEndpointRepo.existsById(endpointID)) {
                return new RedirectView("/proyecto/p/" + id + "/add?error=exist");
            } else {
                MockEndpoint endpoint = new MockEndpoint(endpointID);
                endpoint.setHttpCode(Integer.valueOf(parameterMap.get("codigo")[0]));
                endpoint.setContentType(parameterMap.get("contenttype")[0]);
                endpoint.setName(parameterMap.get("nombre")[0]);
                endpoint.setDescription(parameterMap.get("descripcion")[0]);

                endpoint.setBody(parameterMap.get("cuerpo")[0]);
                endpoint.setJwtRequired(Boolean.valueOf(parameterMap.get("jwtrequired")[0]));
                Timestamp timestamp = Timestamp.from(Instant.now());
                switch (parameterMap.get("limit")[0]) {
                    case "mes":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusMonths(1));
                        break;
                    case "semana":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusWeeks(1));
                        break;
                    case "dia":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusDays(1));
                        break;
                    case "hora":
                        timestamp = timestamp.valueOf(timestamp.toLocalDateTime().plusHours(1));
                    default:
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusYears(1));
                        break;
                }
                endpoint.setExpiration(timestamp);
                endpoint.setSendTime(Integer.valueOf(parameterMap.get("demora")[0]));
                if (endpoint.getJwtRequired()) {
                    endpoint.setJwtToken(tokenGenerator(authService.getUserAuthenticated(),
                            new Date(endpoint.getExpiration().getTime())));
                } else {
                    endpoint.setJwtToken("");
                }
                Map<String, String> headers = new HashMap<>();
                int headersNum = Integer.valueOf(parameterMap.get("headersNum")[0]);
                for (int i = 1; i <= headersNum; i++) {
                    String j = Integer.toString(i);
                    headers.put(parameterMap.get("key" + j)[0], parameterMap.get("value" + j)[0]);
                }
                endpoint.setHeaders(headers);
                mockEndpointRepo.save(endpoint);
            }

        }
        return new RedirectView("/proyecto/p/" + id);
    }

    // crearendpoint
    @RequestMapping("/crearendpoint")
    public String addWithoutProject(HttpServletRequest request) {
        MockProject proyectoD = null;
        Map<String, String[]> parameterMap = request.getParameterMap();
        String id = "";
        if (parameterMap.get("proyecto")[0].equalsIgnoreCase("0default")) {
            List<MockProject> proyectosUsuario =
                    mockProyectoRepo.findAllByUsuarioId(authService.getUserAuthenticated().getUsername());
            if (proyectosUsuario.size() > 0) {
                proyectoD = proyectosUsuario.get(0);
            } else {
                MockProject nuevoProyecto = new MockProject();
                nuevoProyecto.setUsuarioId(authService.getUserAuthenticated().getUsername());
                mockProyectoRepo.save(nuevoProyecto);
                proyectosUsuario =
                        mockProyectoRepo.findAllByUsuarioId(authService.getUserAuthenticated().getUsername());
                proyectoD = proyectosUsuario.get(0);
            }
            id = proyectoD.getId();
        } else {
            id = parameterMap.get("proyecto")[0];
            proyectoD = mockProyectoRepo.findById(parameterMap.get("proyecto")[0]).orElse(null);
        }



        if (proyectoD != null) {
            if (!authService.containsRole("ROLE_ADMIN") && !proyectoD.getUsuarioId().equals(authService.getUserAuthenticated().getUsername())) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "No tiene permiso para añadir endpoint a este proyecto."
                );
            }


            String path = parameterMap.get("ruta")[0];
            if (path.charAt(0) != '/') {
                path = '/' + path;
            }
            MockEndpointPK endpointID = new MockEndpointPK(id, MockEndpointsMethods.valueOf(parameterMap.get("getRadio")[0]), path);
            if (mockEndpointRepo.existsById(endpointID)) {
                return "redirect:/proyecto/p/" + id + "/add?error=exist";
            } else {
                MockEndpoint endpoint = new MockEndpoint(endpointID);
                endpoint.setHttpCode(Integer.valueOf(parameterMap.get("codigo")[0]));
                endpoint.setContentType(parameterMap.get("contenttype")[0]);
                endpoint.setName(parameterMap.get("nombre")[0]);
                endpoint.setDescription(parameterMap.get("descripcion")[0]);

                endpoint.setBody(parameterMap.get("cuerpo")[0]);
                endpoint.setJwtRequired(Boolean.valueOf(parameterMap.get("jwtrequired")[0]));
                Timestamp timestamp = Timestamp.from(Instant.now());
                switch (parameterMap.get("limit")[0]) {
                    case "mes":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusMonths(1));
                        break;
                    case "semana":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusWeeks(1));
                        break;
                    case "dia":
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusDays(1));
                        break;
                    case "hora":
                        timestamp = timestamp.valueOf(timestamp.toLocalDateTime().plusHours(1));
                    default:
                        timestamp = Timestamp.valueOf(timestamp.toLocalDateTime().plusYears(1));
                        break;
                }
                endpoint.setExpiration(timestamp);
                endpoint.setSendTime(Integer.valueOf(parameterMap.get("demora")[0]));
                if (endpoint.getJwtRequired()) {
                    endpoint.setJwtToken(tokenGenerator(authService.getUserAuthenticated(),
                            new Date(endpoint.getExpiration().getTime())));
                } else {
                    endpoint.setJwtToken("");
                }
                Map<String, String> headers = new HashMap<>();
                int headersNum = Integer.valueOf(parameterMap.get("headersNum")[0]);
                for (int i = 1; i <= headersNum; i++) {
                    String j = Integer.toString(i);
                    headers.put(parameterMap.get("key" + j)[0], parameterMap.get("value" + j)[0]);
                }
                endpoint.setHeaders(headers);
                mockEndpointRepo.save(endpoint);
            }

        }
        return "redirect:/proyecto/p/" + id;
    }
    @GetMapping("/p/{id}/delete/{method}/{*path}")
    public RedirectView deleteEndpoint(@PathVariable("id") String id, @PathVariable("method") String method, @PathVariable("path") String path) {
        if (!authService.containsRole("ROLE_ADMIN") && !mockProyectoRepo.existsByIdAndUsuarioId(id, authService.getUserAuthenticated().getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No tiene permiso para añadir endpoint a este proyecto."
            );
        }
        mockEndpointRepo.deleteById(new MockEndpointPK(id, MockEndpointsMethods.valueOf(method), path));
        return new RedirectView("/proyecto/p/" + id);
    }

    /**
     *
     * @param usuario
     * @return
     */
    private String tokenGenerator(User usuario, Date expirationDate) {

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(usuario.getNombre())
                .claim("roles",usuario.getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,
                        llaveSecreta.getBytes()).compact();

        return "Bearer " + token;
    }
}
