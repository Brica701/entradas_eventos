package org.example.entradas_eventos.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.example.entradas_eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EventoService {
    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarEventos() {
        log.info("Servicio: obteniendo lista de eventos");
        return eventoRepository.listarEventos();
    }

    public Evento obtenerPorId(Integer id) {
        return eventoRepository.obtenerPorId(id);
    }
    // --- LÓGICA DE NEGOCIO ---
    public BigDecimal calcularPrecioUnitario(Evento evento, String zona) {
        BigDecimal precio = evento.getPrecioBase();
        if ("GRADA".equalsIgnoreCase(zona)) {
            precio = precio.add(evento.getRecargoGrada());
        } else if ("VIP".equalsIgnoreCase(zona)) {
            precio = precio.add(evento.getRecargoVip());
        }
        return precio;
    }

    //Compras
    public void guardarCompra(CompraEntrada compra) {
        Evento evento = obtenerPorId(compra.getEventoId());
        compra.setPrecioUnitario(calcularPrecioUnitario(evento, compra.getZona()));
        compra.setPrecioTotal(compra.getPrecioUnitario().multiply(BigDecimal.valueOf(compra.getNumeroEntradas())));
        eventoRepository.guardarCompra(compra);
    }

}

