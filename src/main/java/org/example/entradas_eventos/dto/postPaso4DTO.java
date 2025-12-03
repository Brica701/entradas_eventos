package org.example.entradas_eventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class postPaso4DTO {
    @NotBlank(message = "Debe ingresar su nombre")
    private String nombre;

    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "Debe ingresar su email")
    private String email;
}
