package org.example.entradas_eventos.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/eventos")
@SessionAttributes("evento")
public class EventoController {

    private EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/comprar/paso1")
    public String getPaso1(Evento evento, Model model){

        // Obtener lista de eventos desde el servicio
        List<Evento> eventos = eventoService.listarEventos();
        model.addAttribute("eventos", eventos);

        // Crear un objeto CompraEntrada vacío para Thymeleaf
        model.addAttribute("compraEntrada", new CompraEntrada());

        return "paso1"; // Thymeleaf view

    }

    @GetMapping("/comprar/paso2")
    public String getPaso2(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada, Model model) {
        // Recuperar el evento seleccionado desde el servicio
        Evento evento = eventoService.obtenerPorId(compraEntrada.getEventoId());

        // Pasamos los datos a la vista
        model.addAttribute("evento", evento);

        // Opciones de zona
        model.addAttribute("zonas", List.of("GENERAL", "GRADA", "VIP"));

        return "paso2"; // nombre de la plantilla Thymeleaf
    }

    @PostMapping("/comprar/paso2")
    public String postPaso2(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada) {
        // Se guarda la zona elegida en el objeto de sesión
        // Paso siguiente: Paso 3
        return "redirect:/eventos/comprar/paso3";
    }
    @GetMapping("/comprar/paso3")
    public String getPaso3(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada, Model model) {
        // Recuperar el evento seleccionado
        Evento evento = eventoService.obtenerPorId(compraEntrada.getEventoId());
        model.addAttribute("evento", evento);

        // Calcular precio unitario según la zona
        BigDecimal precioUnitario = evento.getPrecioBase();
        if ("GRADA".equalsIgnoreCase(compraEntrada.getZona())) {
            precioUnitario = precioUnitario.add(evento.getRecargoGrada());
        } else if ("VIP".equalsIgnoreCase(compraEntrada.getZona())) {
            precioUnitario = precioUnitario.add(evento.getRecargoVip());
        }

        compraEntrada.setPrecioUnitario(precioUnitario);
        compraEntrada.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(compraEntrada.getNumeroEntradas())));

        model.addAttribute("compraEntrada", compraEntrada);

        return "paso3"; // nombre de la vista Thymeleaf
    }

    @PostMapping("/comprar/paso3")
    public String postPaso3(@ModelAttribute("compraEntrada") CompraEntrada compraEntrada) {
        // Fecha de compra
        compraEntrada.setFechaCompra(LocalDateTime.now());

        // Guardar en la base de datos
        eventoService.guardarCompra(compraEntrada);

        // Redirigir a pantalla final de confirmación
        return "redirect:/eventos/comprar/final";
    }

}
