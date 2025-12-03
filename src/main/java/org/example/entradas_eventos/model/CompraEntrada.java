package org.example.entradas_eventos.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Integer numeroEntradas;
    private String zona;
    private String nombreComprador;
    private String emailComprador;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private LocalDateTime fechaCompra;

}