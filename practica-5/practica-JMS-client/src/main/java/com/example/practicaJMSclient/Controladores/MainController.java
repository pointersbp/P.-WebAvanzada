package com.example.practicaJMSclient.Controladores;

import com.example.practicaJMSclient.PracticaJmsClientApplication;
import com.example.practicaJMSclient.Entidades.Client;
import com.example.practicaJMSclient.Entidades.TailType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${app.server}")
    private String serverAddress;
    @Value("${app.nombrecola}")
    private String nombreCola;
    @RequestMapping("/")
    public String index(Model model) {
        return "redirect:"+serverAddress;
    }

    @RequestMapping("/clientes")
    private String clientes(Model model) {
        model.addAttribute("listaClientes", PracticaJmsClientApplication.getMisClientes());
        return "clientes";
    }

    @PostMapping("/agregarCliente")
    private String agregaCliente(Model model) {
        Client nuevo = new Client(PracticaJmsClientApplication.getCounter(),
                nombreCola, TailType.TOPIC);
        PracticaJmsClientApplication.getMisClientes().add(nuevo);
        PracticaJmsClientApplication.setCounter(PracticaJmsClientApplication.getCounter() + 1);
        return "redirect:/clientes";
    }

    @PostMapping("/eliminarCliente")
    private String eliminarCliente(Model model,
                                   @RequestParam String id) {
        long idCliente = Long.valueOf(id);
        int idLista = -1;
        // Ver su Id.
        for (int i = 0; i < PracticaJmsClientApplication.getMisClientes().size(); i++) {
            if (idCliente == PracticaJmsClientApplication
                    .getMisClientes().get(i).getIdCliente()) {
                idLista = i;
                i+=PracticaJmsClientApplication.getMisClientes().size();
            }
        }
        // Remover cliente.
        if (idLista < PracticaJmsClientApplication.getMisClientes().size() &&
                idLista >= 0) {
            PracticaJmsClientApplication.getMisClientes().remove(
                    PracticaJmsClientApplication.getMisClientes().get(idLista));
        }
        return "redirect:/clientes";
    }

    @RequestMapping("/graficos")
    private String graficos(Model model) {
        return "redirect:"+serverAddress+"/graficos";
    }
}
