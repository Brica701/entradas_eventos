package org.example.entradas_eventos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraEntrada {
    private Integer id;                   // id auto-increment
    private Integer eventoId;             // foreign key hacia Evento
    private String nombreComprador;
    private String emailComprador;
    private Integer numeroEntradas;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private LocalDateTime fechaCompra;
}
