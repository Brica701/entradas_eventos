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

    @NotNull(message = "Debe seleccionar un evento")
    private Integer eventoId;

    @NotNull(message = "Debe indicar el número de entradas")
    @Min(value = 1, message = "Debe comprar al menos una entrada")
    private Integer numeroEntradas;

    @NotBlank(message = "Debe seleccionar una zona")
    private String zona;

    @NotBlank(message = "Debe ingresar su nombre")
    private String nombreComprador;

    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "Debe ingresar su email")
    private String emailComprador;

    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private LocalDateTime fechaCompra;

}