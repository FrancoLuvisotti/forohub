package com.francoLuvisotti.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizacionTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {}
