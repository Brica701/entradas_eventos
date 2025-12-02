package org.example.entradas_eventos.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes("evento")
@RequestMapping("/eventos")
public class AdminController {
    final private EventoRepository eventoRepository;
    final private EventoService eventoService;
    public AdminController(EventoRepository eventoRepository, EventoService eventoService) {
        this.eventoRepository = eventoRepository;
        this.eventoService = eventoService;
    }


    @GetMapping("/index")
    public String index(Model model) {
        List<Evento> eventos = eventoService.findAllEvents();
        model.addAttribute("eventos", eventos);

        return "index";
    }
    @GetMapping("/añadir")
    public String aniadirGet(Model model) {
        model.addAttribute("evento",new Evento());
        return "añadir";
    }
    @PostMapping("/añadir")
    public String aniadir(Evento evento, Model model) {

        eventoRepository.createEvento(evento);
        model.addAttribute("evento", evento);
        return "redirect:/eventos/index";
    }

    @GetMapping("/editar/{id}")
    public String editarGet(Model model, @PathVariable Integer id) {
        Evento evento = eventoService.findEventoId(id);
        model.addAttribute("evento",evento);
        return "editar";
    }

    @PostMapping("/editar")
    public String editar(Evento evento, Model model) {
        eventoRepository.actualizarEvento(evento);
        model.addAttribute("evento", evento);
        return "redirect:/eventos/index";
    }
    @GetMapping("/borrar/{id}")
    public String borrar(Model model, @PathVariable Integer id) {
        Evento evento = eventoService.findEventoId(id);
        model.addAttribute("evento",evento);
        return "borrar";
    }

    @PostMapping("/borrar")
    public String borrar(Evento evento, Model model) {
        eventoRepository.eliminarEvento(evento.getId());
        model.addAttribute("evento", evento);
        return "redirect:/eventos/index";
    }

}