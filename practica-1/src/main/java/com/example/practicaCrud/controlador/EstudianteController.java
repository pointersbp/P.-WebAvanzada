package com.example.practicaCrud.controlador;

import com.example.practicaCrud.entidades.Estudiante;
import com.example.practicaCrud.servicios.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class EstudianteController {

    @Autowired
    private EstudianteServicio estudianteService;

    @GetMapping("/")
    public String listaEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevoEstudianteForm(Model model) {
        Estudiante estudiante = new Estudiante();
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("action", "/guardar");
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante) {
        estudianteService.saveEstudiante(estudiante);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editarEstudianteForm(@PathVariable("id") Long id, Model model) {
        Estudiante estudiante = estudianteService.getEstudianteById(id);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("action", "/actualizar");
        return "formulario";
    }

    @PostMapping("/actualizar")
    public String actualizarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante) {
        estudianteService.updateEstudiante(estudiante);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable("id") Long id) {
        estudianteService.deleteEstudiante(id);
        return "redirect:/";
    }
}