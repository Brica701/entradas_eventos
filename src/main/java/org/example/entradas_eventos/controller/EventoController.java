package org.example.entradas_eventos.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.dto.PostPaso2DTO;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@SessionAttributes({"evento","compraEntrada"})
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    private final EventoRepository eventoRepository;

    public EventoController(EventoService eventoService, EventoRepository eventoRepository) {
        this.eventoService = eventoService;
        this.eventoRepository = eventoRepository;
    }



    @ModelAttribute("evento")
    public Evento initEvento() {
        return new Evento();
    }

    @ModelAttribute("compraEntrada")
    public CompraEntrada initCompraEntrada() {
        return new CompraEntrada();
    }


    @GetMapping("/calcular/paso1")
    public String calcularPaso1(Evento evento, Model model, CompraEntrada compraEntrada) {

        log.info("calcularPaso1 {}",evento);
        List<Evento> eventos = eventoService.findAllEvents();
        model.addAttribute("eventos", eventos);
        model.addAttribute("eventoGet",evento);
        model.addAttribute("compraEntrada",compraEntrada);
        return "paso1";
    }

    @PostMapping("/calcular/paso2")
    public String calcularPaso2Post( @ModelAttribute PostPaso2DTO postPaso2DTO, HttpSession httpSession, Model model) {
        log.info("calcularPaso2Post", postPaso2DTO);

        Evento eventoBd = eventoService.findEventoId(postPaso2DTO.getEventoId());
        CompraEntrada compraEntrada = CompraEntrada.builder()
                .numeroEntrada(postPaso2DTO.getCantidadEntradas())
                .build();
        compraEntrada.setEventoId(postPaso2DTO.getEventoId());
        compraEntrada.setFechaCompra(LocalDateTime.now());
        compraEntrada.setPrecioUnitario(eventoBd.getPrecioBase().add(eventoBd.getRecargoVip()));
        compraEntrada.setPrecioTotal(compraEntrada.getPrecioUnitario().multiply(BigDecimal.valueOf(compraEntrada.getNumeroEntrada())));
        httpSession.setAttribute("evento", eventoBd);
        httpSession.setAttribute("compraEntrada",compraEntrada);
        model.addAttribute("evento",eventoBd);
        model.addAttribute("compraEntrada", compraEntrada);

        return "paso2";
    }

    @PostMapping("/calcular/paso3")
    public String calcularPaso3Post( HttpSession httpSession, Model model,@RequestParam("zona") String zona) {
        Evento eventoBd = (Evento) httpSession.getAttribute("evento");
        CompraEntrada compraEntrada = (CompraEntrada) httpSession.getAttribute("compraEntrada");
        //como no tenemos un th field usamos el requestParam de zona y lo metemos a mano
        compraEntrada.setZona(zona);
        httpSession.setAttribute("evento", eventoBd);
        model.addAttribute("evento",eventoBd);
        model.addAttribute("compraEntrada",compraEntrada);
        return "paso3";
    }

    @PostMapping("/calcular/paso4")
    public String caluclarPaso4Post(HttpSession httpSession, Model model, CompraEntrada compraEntrada) {
        Evento eventoBd = (Evento) httpSession.getAttribute("evento");

        eventoRepository.createEntrada(compraEntrada);
        httpSession.setAttribute("evento", eventoBd);
        httpSession.setAttribute("compraEntrada",compraEntrada);
        model.addAttribute("evento",eventoBd);
        model.addAttribute("compraEntrada",compraEntrada);
        return "paso4";
    }

}
