package org.example.entradas_eventos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Evento {
    private long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha_hora;
    private String lugar;
    private BigDecimal precio_base;
    private BigDecimal recargo_grada;
    private BigDecimal recargo_vip;


    public static void autoMapper(Evento evento1, Evento evento2) {
        evento2.setId(evento1.getId());
        evento2.setNombre(evento1.getNombre());
        evento2.setDescripcion(evento1.getDescripcion());
        evento2.setFecha_hora(evento1.getFecha_hora());
        evento2.setLugar(evento1.getLugar());
        evento2.setPrecio_base(evento1.getPrecio_base());
        evento2.setRecargo_grada(evento1.getRecargo_grada());
        evento2.setRecargo_vip(evento1.getRecargo_vip());
    }
}