package com.example.CartBase.Controladores;

import com.example.CartBase.Entidades.Service;
import com.example.CartBase.Repositorios.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas/servicios")
public class ServiceController {
    @Autowired
    ServiceRepository servicioRepo;

    @GetMapping("")
    public List<Service> getList(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return servicioRepo.findAll(PageRequest.of(page, size)).toList();
    }

    @PostMapping("")
    public Service createServicio(
            @RequestBody Service servicio
    ) {
        servicioRepo.save(servicio);
        return servicio;
    }

    @GetMapping("/{id}")
    public Service getServicio(
            @PathVariable("id") Long id
    ) {
        Optional<Service> optional = servicioRepo.findById(id);
        if (optional.isPresent())
            return optional.get();
        return null;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Service modifyServicio(
            @PathVariable("id") Long id,
            @RequestBody Service servicio
    ) {
        if (id != servicio.getId() || !servicioRepo.existsById(id))
            return null;
        servicioRepo.save(servicio);
        return servicio;
    }

    @DeleteMapping("/{id}")
    public void deleteService(
            @PathVariable("id") Long id
    ) {
        servicioRepo.deleteById(id);
        return;
    }

}
