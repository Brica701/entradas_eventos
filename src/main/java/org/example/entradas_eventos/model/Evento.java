package org.example.entradas_eventos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Evento {
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private String lugar;
    private BigDecimal precioBase;
    private BigDecimal recargoGrada;
    private BigDecimal recargoVip;

}