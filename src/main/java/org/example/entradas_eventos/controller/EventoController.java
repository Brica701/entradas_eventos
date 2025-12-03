package org.example.entradas_eventos.controller;

import jakarta.validation.Valid;
import org.example.entradas_eventos.dto.postPaso2DTO;
import org.example.entradas_eventos.dto.postPaso4DTO;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.example.entradas_eventos.service.EventoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String paso1(Model model, @ModelAttribute("compra") CompraEntrada compra) {
        List<Evento> eventos = repo.findAll();

        postPaso2DTO dto = new postPaso2DTO();
        dto.setEventoId(compra.getEventoId());
        dto.setCantidad(compra.getNumeroEntradas());


        model.addAttribute("eventos", eventos);
        model.addAttribute("postPaso2DTO",  dto);
        return "paso1";
    }

    // Paso 2: seleccionar evento y número de entradas
    @PostMapping("/paso2")
    public String paso2(
            @ModelAttribute("compra") CompraEntrada compra,
            @Valid @ModelAttribute postPaso2DTO postPaso2DTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, recargar la lista de eventos y mostrar paso1
            List<Evento> eventos = repo.findAll();
            model.addAttribute("eventos", eventos);
            return "paso1";
        }

        // Copiar datos del DTO a la sesión 'compra'
        compra.setEventoId(postPaso2DTO.getEventoId());
        compra.setNumeroEntradas(postPaso2DTO.getCantidad());

        // Obtener el evento seleccionado para mostrar en la vista
        Evento e = repo.findById(postPaso2DTO.getEventoId());
        model.addAttribute("evento", e);

        return "paso2";
    }

    @GetMapping("/paso3")
    public String mostrarPaso3(@ModelAttribute("compra") CompraEntrada compra, Model model) {
        Evento e = repo.findById(compra.getEventoId());
        model.addAttribute("evento", e);

        // Crear un DTO con los datos que ya están en la compra (nombre y email)
        postPaso4DTO dto = new postPaso4DTO();
        dto.setNombre(compra.getNombreComprador());
        dto.setEmail(compra.getEmailComprador());
        model.addAttribute("postPaso4DTO", dto);

        return "paso3";
    }

    // Paso 3: seleccionar zona
    @PostMapping("/paso3")
    public String paso3(@ModelAttribute("compra") CompraEntrada compra,
                        @RequestParam String zona,
                        Model model) {

        compra.setZona(zona);

        Evento e = repo.findById(compra.getEventoId());
        model.addAttribute("evento", e);

        // Agregar el DTO
        model.addAttribute("postPaso4DTO", new postPaso4DTO());

        return "paso3";
    }



    // POST Paso4: procesar formulario
    @PostMapping("/paso4")
    public String confirmarCompra(
            @ModelAttribute("compra") CompraEntrada compra,
            @Valid @ModelAttribute("postPaso4DTO") postPaso4DTO dto,
            BindingResult bindingResult,
            Model model) {

        Evento e = repo.findById(compra.getEventoId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("evento", e);
            return "paso3"; // recargamos el formulario con errores
        }

        // Copiar datos del DTO a la sesión
        compra.setNombreComprador(dto.getNombre());
        compra.setEmailComprador(dto.getEmail());

        BigDecimal precioUnitario = service.calcularPrecio(e, compra.getZona());
        compra.setPrecioUnitario(precioUnitario);
        compra.setPrecioTotal(precioUnitario.multiply(BigDecimal.valueOf(compra.getNumeroEntradas())));
        compra.setFechaCompra(LocalDateTime.now());

        repo.createCompra(compra);

        model.addAttribute("evento", e);
        model.addAttribute("compra", compra);

        return "paso4"; // vista final
    }
}
