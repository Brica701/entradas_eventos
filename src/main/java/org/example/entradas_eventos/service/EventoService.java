package org.example.entradas_eventos.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> findAllEvents() {
        return eventoRepository.findAll();
    }

    public Evento findEventoId(int id) {
        return eventoRepository.findById(id);
    }

    // Método correcto para calcular precio según evento y zona
    public BigDecimal calcularPrecio(Evento evento, String zona) {
        BigDecimal precio = evento.getPrecioBase();
        if ("GRADA".equalsIgnoreCase(zona)) {
            precio = precio.add(evento.getRecargoGrada());
        } else if ("VIP".equalsIgnoreCase(zona)) {
            precio = precio.add(evento.getRecargoVip());
        }
        return precio;
    }
}
