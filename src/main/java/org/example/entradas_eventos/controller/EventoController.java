package org.example.entradas_eventos.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.dto.PostPaso2DTO;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/eventos")
@Slf4j
@SessionAttributes({"evento", "compraEntrada"})
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // -------------------- PASO 1 --------------------
    @GetMapping("/paso1")
    public String paso1Get(Evento evento, CompraEntrada compraEntrada, Model model) {
        log.info("paso1Get {}", evento);

        var eventos = eventoService.getAll();
        model.addAttribute("eventos", eventos);
        model.addAttribute("eventoGet", evento);
        model.addAttribute("numeroEntradas", compraEntrada != null ? compraEntrada.getNumeroEntradas() : null);

        // Plantilla directamente en templates/
        return "paso1";
    }

    // -------------------- PASO 2 --------------------
    @PostMapping("/paso2")
    public String postPaso2(Model model, @ModelAttribute PostPaso2DTO postPaso2DTO, HttpSession session) {
        log.info("postPaso2 {}", postPaso2DTO);

        Evento eventoBd;
        try {
            eventoBd = eventoService.findById(postPaso2DTO.getEventoId());
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // Si no existe el evento, redirige a paso1
            log.warn("Evento no encontrado con id {}", postPaso2DTO.getEventoId());
            return "redirect:/eventos/paso1";
        }

        session.setAttribute("evento", eventoBd);
        session.setAttribute("compraEntrada", CompraEntrada.builder()
                .numeroEntradas(postPaso2DTO.getCantidadEntradas())
                .build());

        model.addAttribute("evento", eventoBd);

        // Plantilla directamente en templates/
        return "paso2";
    }

    // -------------------- PASO 3 --------------------
    @GetMapping("/paso3")
    public String paso3Get(Model model, HttpSession session) {
        var evento = (Evento) session.getAttribute("evento");
        var compra = (CompraEntrada) session.getAttribute("compraEntrada");

        if (evento == null || compra == null) {
            return "redirect:/eventos/paso1";
        }

        model.addAttribute("evento", evento);
        model.addAttribute("compraEntrada", compra);

        return "paso3";
    }

    @PostMapping("/paso3")
    public String paso3Post(@ModelAttribute CompraEntrada compraEntradaForm,
                            HttpSession session,
                            Model model) {
        var evento = (Evento) session.getAttribute("evento");
        var compra = (CompraEntrada) session.getAttribute("compraEntrada");

        if (evento == null || compra == null) {
            return "redirect:/eventos/paso1";
        }

        compra.setNombreComprador(compraEntradaForm.getNombreComprador());
        compra.setEmailComprador(compraEntradaForm.getEmailComprador());
        compra.setZona(compraEntradaForm.getZona());

        // Calcular precios
        BigDecimal precioUnitario = evento.getPrecio_base();
        String zona = compra.getZona() != null ? compra.getZona().toUpperCase() : "GENERAL";
        switch (zona) {
            case "GRADA":
                precioUnitario = precioUnitario.add(evento.getRecargo_grada());
                break;
            case "VIP":
                precioUnitario = precioUnitario.add(evento.getRecargo_vip());
                break;
        }
        compra.setPrecioUnitario(precioUnitario);
        compra.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(compra.getNumeroEntradas())));
        compra.setEventoId((int) evento.getId());
        compra.setFechaCompra(LocalDateTime.now());

        model.addAttribute("evento", evento);
        model.addAttribute("compraEntrada", compra);

        return "confirmacion";
    }
}
