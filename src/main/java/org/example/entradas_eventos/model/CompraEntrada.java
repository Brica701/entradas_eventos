package org.example.entradas_eventos.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompraEntrada {
    private Integer id;
    private Integer eventoId;
    private String nombreComprador;
    private String emailComprador;
    private String zona;
    private int numeroEntradas;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private Date fechaCompra;
}