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

@Controller
@RequestMapping("/eventos")
@Slf4j
@SessionAttributes({"evento", "compraEntrada"})
public class EventoController {
    private EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/comprar/paso1")
    public String paso1Get(Evento evento, CompraEntrada compraEntrada,
                           Model model) {

        log.info("paso1Get {}", evento);

        var eventos = eventoService.getAll();

        model.addAttribute("eventos", eventos);
        model.addAttribute("eventoGet", evento);
        model.addAttribute("numeroEntradas", compraEntrada !=  null ? compraEntrada.getNumeroEntradas() : null);

        return "evento/comprar/paso1";
    }


    @PostMapping("/comprar/paso2")
    public String postPaso2(/*Evento evento,*/Model model, @ModelAttribute PostPaso2DTO postPaso2DTO, HttpSession httpSession){

        log.info("postPaso2 {}", postPaso2DTO);

        var eventoBd = eventoService.findById(postPaso2DTO.getEventoId());
        httpSession.setAttribute("evento", eventoBd);
        httpSession.setAttribute("compraEntrada", CompraEntrada.builder().numeroEntradas(postPaso2DTO.getCantidadEntradas()).build());

        model.addAttribute("evento", eventoBd);

        /*Evento.autoMapper(eventoBd, evento);*/

        return "evento/comprar/paso2";
    }

}