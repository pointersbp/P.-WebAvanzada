package com.example.practicaCrud.controlador;

import com.example.practicaCrud.ambientes.AmbienteSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AmbienteSesion ambienteSesion;


    @RequestMapping("/")
    public String index(Principal principal){

        return "Usuario Conectado: "+principal.getName()+" --- "+ambienteSesion.getContador();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/crear")
    public String crear(){
        return "Crear del admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/borrar")
    public String borrar(){
        return "Borrar del admin";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/actualizar")
    public String actualizar(){
        return "Actualizar del admin";
    }
}
