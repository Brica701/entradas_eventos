package org.example.entradas_eventos.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class EventoService {
    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
}
