package com.example.WebAPP.Controladores;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @RequestMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "signin";
    }

    @RequestMapping("/registrar")
    public String registro() {
        return "signup";
    }
// Para redireccionar
    @RequestMapping("/verifyAuthentication")
    public String autenticacion() {
        log.info("Redirect a: /");
        return "redirect:/";
    }
}
