package org.example.entradas_eventos.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/eventos")
@SessionAttributes("compraEntrada")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Inicializa compraEntrada en la sesión si no existe
    @ModelAttribute("compraEntrada")
    public CompraEntrada initCompraEntrada() {
        return new CompraEntrada();
    }

    // -------------------- PASO 1 --------------------
    @GetMapping("/comprar/paso1")
    public String getPaso1(Model model) {
        List<Evento> eventos = eventoService.listarEventos();
        model.addAttribute("eventos", eventos);
        return "paso1";
    }

    @PostMapping("/comprar/paso1")
    public String postPaso1(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada) {
        // Redirige al paso2
        return "redirect:/eventos/comprar/paso2";
    }

    // -------------------- PASO 2 --------------------
    @GetMapping("/comprar/paso2")
    public String getPaso2(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada, Model model) {
        if (compraEntrada.getEventoId() == null) {
            return "redirect:/eventos/comprar/paso1";
        }

        Evento evento = eventoService.obtenerPorId(compraEntrada.getEventoId());
        if (evento == null) {
            return "redirect:/eventos/comprar/paso1";
        }

        model.addAttribute("evento", evento);
        model.addAttribute("zonas", List.of("GENERAL", "GRADA", "VIP"));
        return "paso2";
    }

    @PostMapping("/comprar/paso2")
    public String postPaso2(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada) {
        // Zona ya seleccionada, redirige a paso3
        return "redirect:/eventos/comprar/paso3";
    }

    // -------------------- PASO 3 --------------------
    @GetMapping("/comprar/paso3")
    public String getPaso3(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada, Model model) {
        if (compraEntrada.getEventoId() == null || compraEntrada.getZona() == null) {
            return "redirect:/eventos/comprar/paso1";
        }

        Evento evento = eventoService.obtenerPorId(compraEntrada.getEventoId());
        model.addAttribute("evento", evento);

        // Calcular precios
        BigDecimal precioUnitario = evento.getPrecioBase();
        if ("GRADA".equalsIgnoreCase(compraEntrada.getZona())) {
            precioUnitario = precioUnitario.add(evento.getRecargoGrada());
        } else if ("VIP".equalsIgnoreCase(compraEntrada.getZona())) {
            precioUnitario = precioUnitario.add(evento.getRecargoVip());
        }

        compraEntrada.setPrecioUnitario(precioUnitario);
        compraEntrada.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(compraEntrada.getNumeroEntradas())));
        model.addAttribute("compraEntrada", compraEntrada);

        return "paso3";
    }

    @PostMapping("/comprar/paso3")
    public String postPaso3(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada) {
        // Guardar compra
        eventoService.guardarCompra(compraEntrada);
        // Aquí puedes redirigir al inicio o mostrar un mensaje de éxito
        return "redirect:/eventos/comprar/paso1";
    }
}
