package com.example.WebAPP.Controladores;

import com.example.WebAPP.Utilidades.Enumerate.StatusOrder;
import com.example.WebAPP.Utilidades.OrderList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashController.class);

    @Value("${proyecto.api.url}")
    String apiUrl;

    RestTemplate restTemplate = new RestTemplate();

    StatusOrder[] ordenStatuses = new StatusOrder[]{StatusOrder.ANY, StatusOrder.CREATED, StatusOrder.PROGRESS, StatusOrder.COMPLETED};

    @RequestMapping
    public String principal() {
        return "dashboard";
    }

    @RequestMapping("/usuarios")  // Control de usuarios (administradores)
    public String controlUsuarios() {
        return "users";
    }

    @RequestMapping("/perfil")    // Perfil
    public String perfil() {
        return "profile";
    }

    @RequestMapping("/tienda")    // Tienda
    public String tienda() {
        return "shop";
    }


    @RequestMapping("/ordenes")   // Ordenes en curso.
    public String ordenes(
            Model model,
            @RequestParam(value = "nombreUsuario", required = false, defaultValue = "") String username,
            @RequestParam(value = "status", required = false, defaultValue = "ANY") StatusOrder status,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        String query = "?page=" + page;
        String postQuery = "";
        if (status != StatusOrder.ANY) {
            postQuery += "&status=" + status.toString();
        }
        if (username.length() > 0) {
            postQuery += "&nombreUsuario=" + username;
        }
        ResponseEntity<OrderList> response = restTemplate.exchange(
                apiUrl + "/api/ventas/order" + query + postQuery,
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders() {{
                            setBearerAuth(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
                        }}
                ),
                OrderList.class
        );
        OrderList responseBody = response.getBody();
        model.addAttribute("ordenes", responseBody.getOrdenList());
        model.addAttribute("username", username);
        model.addAttribute("statuses", ordenStatuses);
        model.addAttribute("cStatus", status);
        model.addAttribute("maxPage", responseBody.getMaxPage());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageLink", "/dashboard/ordenes?" + postQuery);
        return "ordenes";
    }

    @RequestMapping("/compras")   // Historial de compra (clientes)
    public String compras(
            Model model,
            @RequestParam(value = "status", required = false, defaultValue = "ANY") StatusOrder status,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        String query = "?page=" + page;
        String postQuery = "";
        if (status != StatusOrder.ANY) {
            postQuery += "&status=" + status.toString();
        }
        ResponseEntity<OrderList> response = restTemplate.exchange(
                apiUrl + "/api/ventas/order" + query + postQuery,
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders() {{
                            setBearerAuth(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
                        }}
                ),
                OrderList.class
        );
        OrderList responseBody = response.getBody();
        model.addAttribute("cliente", true);
        model.addAttribute("ordenes", responseBody.getOrdenList());
        model.addAttribute("username", "");
        model.addAttribute("statuses", ordenStatuses);
        model.addAttribute("cStatus", status);
        model.addAttribute("maxPage", responseBody.getMaxPage());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageLink", "/dashboard/compras?" + postQuery);
        return "ordenes";
    }
}
