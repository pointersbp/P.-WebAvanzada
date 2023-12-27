package com.example.CartBase.Controladores;

import com.example.CartBase.Entidades.Enumerate.OrderStatus;
import com.example.CartBase.Entidades.Order;
import com.example.CartBase.Entidades.Service;
import com.example.CartBase.Repositorios.OrderRepository;
import com.example.CartBase.Repositorios.ServiceRepository;
import com.example.CartBase.Servicios.PaypalService;
import com.example.CartBase.Utilidades.OrderList;
import com.example.CartBase.Utilidades.Paypal.PaypalLinkDescription;
import com.example.CartBase.Utilidades.Paypal.PaypalOrder;
import com.example.CartBase.Utilidades.Paypal.PaypalPurchaseUnit;
import com.example.CartBase.Utilidades.Paypal.PaypalPurchaseUnitItem;
import com.example.CartBase.Utilidades.Paypal.enumerate.PaypalHttpMethod;
import com.example.CartBase.Utilidades.RequestNewOrder;
import com.example.CartBase.Utilidades.Resume;
import com.example.CartBase.Utilidades.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/ventas/order")
public class OrderController {
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private ServiceRepository servicioRepo;

    @Autowired
    private OrderRepository ordenRepo;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @GetMapping(value = "/paypal/{id}")
    public Summary orden(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PaypalOrder paypalOrder = paypalService.getPaypalOrderData(id);
        PaypalPurchaseUnit paypalPurchaseUnit = paypalOrder.getPurchaseUnits().stream().findFirst().get();
        PaypalPurchaseUnitItem item = paypalPurchaseUnit.getItems().stream().findFirst().get();
        if(!paypalPurchaseUnit.getCustom_id().equals(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este no es su factura.");
        Service servicio = servicioRepo.findById(Long.valueOf(item.getSku())).get();
        String fecha = paypalPurchaseUnit.getDescription();
        return new Summary(servicio, fecha);
    }

    //crear una orden, retorna un link para que redireccione al usuario.
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createOrder(@RequestBody RequestNewOrder requestNewOrder) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Service> servicio = servicioRepo.findById(requestNewOrder.getServicioId());
        if (!servicio.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay servicios declarados.");
        }
        try {
            simpleDateFormat.parse(requestNewOrder.getDueDatetime());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        List<Service> servicios = new ArrayList<>();
        servicios.add(servicio.get());
        PaypalOrder order = paypalService.createOrder(
                auth.getName(),
                requestNewOrder.getReturnUrl(),
                requestNewOrder.getCancelUrl(),
                servicios,
                requestNewOrder.getDueDatetime()
        );
        PaypalLinkDescription approveLink = order.getLinks().stream().filter(link -> link.getRel().equalsIgnoreCase("approve") && link.getMethod().equals(PaypalHttpMethod.GET)).findFirst().get();
        return approveLink.getHref();
    }

    @GetMapping("")
    public OrderList getList(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "status", required = false) OrderStatus status
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Order> ordenes = null;
        if (auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROL_CLIENTE"))) {
            String clienteUsername = auth.getName();
            if (status == null)
                ordenes = ordenRepo.findAllByClientUsername(clienteUsername, pageable);
            else
                ordenes = ordenRepo.findAllByClientUsernameAndStatus(clienteUsername, status, pageable);
        } else if (status != null && username != null) {
            ordenes = ordenRepo.findAllByClientUsernameStartsWithAndStatus(username, status, pageable);
        } else if (status != null && username == null)
            ordenes = ordenRepo.findAllByStatus(status, pageable);
        else if (status == null && username != null)
            ordenes = ordenRepo.findAllByClientUsernameStartsWith(username, pageable);
        else
            ordenes = ordenRepo.findAll(pageable).toList();
        return new OrderList(
                size,
                ordenes.size() / size + 1,
                ordenes
        );
    }

    @GetMapping("/{id}")
    public Order getOrden(
            @PathVariable("id") Long id
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Order> optional = null;
        if (auth.isAuthenticated() && auth.getAuthorities().contains("ROL_CLIENTE")) {
            String clienteUsername = auth.getName();
            optional = ordenRepo.findByIdAndClientUsername(id, clienteUsername);
        } else {
            ordenRepo.findById(id);
        }

        if (optional.isPresent())
            return optional.get();
        return null;
    }

    @PostMapping(value = "/{id}/accept")
    public Order acceptOrder(
            @PathVariable("id") Long id
    ) {
        Optional<Order> optional = ordenRepo.findByIdAndStatus(id, OrderStatus.CREATED);
        if (optional.isPresent()) {
            Date date = new Date();
            Order orden = optional.get();
            orden.setStatus(OrderStatus.PROGRESS);
            orden.setAcceptedDate(date);
            orden.setUpdateDate(date);
            orden = ordenRepo.save(orden);
            return orden;
        }
        return null;
    }

    @PostMapping(value = "/{id}/complete")
    public Order completeOrder(
            @PathVariable("id") Long id
    ) {
        //Todo: set employee username
        Optional<Order> optional = ordenRepo.findByIdAndStatus(id, OrderStatus.PROGRESS);
        if (optional.isPresent()) {
            Order orden = optional.get();
            orden.setStatus(OrderStatus.COMPLETED);
            orden.setUpdateDate(new Date());
            orden = ordenRepo.save(orden);
            return orden;
        }
        return null;
    }

    @PostMapping(value = "/resumen")
    public List<Resume> getResumenes() {
        List<Resume> resumenes = new ArrayList<>();
        Date hoy = Date.from(LocalDateTime.now().minusDays(6).atZone(ZoneId.systemDefault()).toInstant());
        Calendar c = Calendar.getInstance();
        c.setTime(hoy);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 7; i++) {
            hoy = c.getTime();
            c.add(Calendar.DATE, 1);
            Date despues = c.getTime();
            resumenes.add(new Resume(
                    ordenRepo.countAllByCreateDateBetween(hoy, despues),
                    ordenRepo.countAllByAcceptedDateBetween(hoy, despues),
                    ordenRepo.countAllByUpdateDateBetweenAndStatus(hoy, despues, OrderStatus.COMPLETED),
                    hoy
            ));
        }
        return resumenes;
    }
}
