package com.francoLuvisotti.forohub.dto;

import com.francoLuvisotti.forohub.domain.topico.Topico;

import java.time.LocalDateTime;

public record ListadoTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Topico.StatusTopico status,
        String autor,
        String curso
) {}
