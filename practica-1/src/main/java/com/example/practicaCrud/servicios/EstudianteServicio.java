package com.example.practicaCrud.servicios;

import com.example.practicaCrud.entidades.Estudiante;
import com.example.practicaCrud.repositorios.seguridad.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstudianteServicio {
    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteServicio(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    //Guardar un estudiante
    public void saveEstudiante(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    //Actualizar un estudiante
    public void updateEstudiante(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    //Obtener todos los estudiantes
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    //Método para obtener un estudiante por su ID
    public Estudiante getEstudianteById(Long id) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);
        return estudianteOptional.orElse(null);
    }

    //Método para eliminar un estudiante por su ID
    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }
}

