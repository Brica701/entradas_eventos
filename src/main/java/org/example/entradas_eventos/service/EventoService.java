package org.example.entradas_eventos.service;

import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {
    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> getAll(){
        return eventoRepository.getAll();
    }

    public Evento findById(Long id){
        return eventoRepository.findById(id);
    }
}

