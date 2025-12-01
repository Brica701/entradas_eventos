package org.example.entradas_eventos.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EventoService {
    @Autowired
    private  final EventoRepository eventoRepository;
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> findAllEvents() {
        return eventoRepository.findAll();
    }

    public Evento findEventoId (int id){
        return eventoRepository.findById(id);
    }



    public Double calcularPrecio(String tipoEntrada){
        double precio = 30;
        if(tipoEntrada.equalsIgnoreCase("grada")){
            precio += 15;

        }else if(tipoEntrada.equalsIgnoreCase("vip")){
            precio += 50;
        }

        return precio;
    }
}
