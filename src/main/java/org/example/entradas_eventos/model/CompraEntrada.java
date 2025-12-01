package org.example.entradas_eventos.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private Integer numeroEntrada;

    private BigDecimal precioUnitario;

    private BigDecimal precioTotal;

    private LocalDateTime fechaCompra;

}