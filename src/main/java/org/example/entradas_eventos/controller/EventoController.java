package org.example.entradas_eventos.controller;

import jakarta.servlet.http.HttpSession;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository repo;
    private final EventoService service;

    public EventoController(EventoRepository repo, EventoService service) {
        this.repo = repo;
        this.service = service;
    }

    // Paso 1: mostrar eventos
    @GetMapping("/paso1")
    public String paso1(Model model) {
        List<Evento> eventos = repo.findAll();
        model.addAttribute("eventos", eventos);
        return "paso1";
    }

    @PostMapping("/paso2")
    public String paso2(@RequestParam int eventoId, @RequestParam int cantidad, HttpSession session, Model model) {
        Evento e = repo.findById(eventoId);
        CompraEntrada c = new CompraEntrada();
        c.setEventoId(eventoId);
        c.setNumeroEntradas(cantidad);

        session.setAttribute("evento", e);
        session.setAttribute("compra", c);

        model.addAttribute("evento", e);
        return "paso2";
    }

    // Paso 2: seleccionar zona
    @PostMapping("/paso3")
    public String paso3(@RequestParam String zona, HttpSession session, Model model) {
        CompraEntrada c = (CompraEntrada) session.getAttribute("compra");
        Evento e = (Evento) session.getAttribute("evento");

        c.setZona(zona);
        model.addAttribute("evento", e);
        model.addAttribute("compra", c);
        return "paso3";
    }

    // Paso 3: datos comprador y confirmación
    @PostMapping("/paso4")
    public String paso4(@RequestParam String nombre, @RequestParam String email, HttpSession session, Model model) {
        CompraEntrada c = (CompraEntrada) session.getAttribute("compra");
        Evento e = (Evento) session.getAttribute("evento");

        c.setNombreComprador(nombre);
        c.setEmailComprador(email);
        c.setFechaCompra(LocalDateTime.now());

        // calcular precios
        BigDecimal precioUnitario = service.calcularPrecio(e, c.getZona());
        c.setPrecioUnitario(precioUnitario);
        c.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(c.getNumeroEntradas())));

        // guardar en BD
        repo.createCompra(c);

        model.addAttribute("evento", e);
        model.addAttribute("compra", c);
        return "paso4";
    }
}
