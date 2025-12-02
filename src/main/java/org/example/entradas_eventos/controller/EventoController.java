package org.example.entradas_eventos.controller;

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
@SessionAttributes("compra") // Guardamos la compra en sesión automáticamente
public class EventoController {

    private final EventoRepository repo;
    private final EventoService service;

    public EventoController(EventoRepository repo, EventoService service) {
        this.repo = repo;
        this.service = service;
    }

    // Inicializamos el objeto de sesión
    @ModelAttribute("compra")
    public CompraEntrada initCompra() {
        return new CompraEntrada();
    }

    // Paso 1: mostrar eventos
    @GetMapping("/paso1")
    public String paso1(Model model) {
        List<Evento> eventos = repo.findAll();
        model.addAttribute("eventos", eventos);
        return "paso1";
    }

    // Paso 2: seleccionar evento y número de entradas
    @PostMapping("/paso2")
    public String paso2(@ModelAttribute("compra") CompraEntrada compra,
                        @RequestParam int eventoId,
                        @RequestParam int cantidad,
                        Model model) {

        Evento e = repo.findById(eventoId);

        compra.setEventoId(eventoId);
        compra.setNumeroEntradas(cantidad);

        model.addAttribute("evento", e);
        return "paso2";
    }

    // Paso 3: seleccionar zona
    @PostMapping("/paso3")
    public String paso3(@ModelAttribute("compra") CompraEntrada compra,
                        @RequestParam String zona,
                        Model model) {

        Evento e = repo.findById(compra.getEventoId());
        compra.setZona(zona);

        model.addAttribute("evento", e);
        return "paso3";
    }

    // Paso 4: datos del comprador y confirmación
    @PostMapping("/paso4")
    public String paso4(@ModelAttribute("compra") CompraEntrada compra,
                        @RequestParam String nombre,
                        @RequestParam String email,
                        Model model) {

        Evento e = repo.findById(compra.getEventoId());

        compra.setNombreComprador(nombre);
        compra.setEmailComprador(email);
        compra.setFechaCompra(LocalDateTime.now());

        // Calcular precios
        BigDecimal precioUnitario = service.calcularPrecio(e, compra.getZona());
        compra.setPrecioUnitario(precioUnitario);
        compra.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(compra.getNumeroEntradas())));

        // Guardar en BD
        repo.createCompra(compra);

        model.addAttribute("evento", e);
        return "paso4";
    }
}
