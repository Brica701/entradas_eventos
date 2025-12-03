package org.example.entradas_eventos.dto;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class postPaso2DTO {
    @NotNull(message = "Debe seleccionar un evento")
    private Integer eventoId;

    @NotNull(message = "Debe indicar el número de entradas")
    @Min(value = 1, message = "Debe comprar al menos una entrada")
    @Max(value = 20, message = "Debe comprar menos de 20 entradas")
    private Integer cantidad;
}
