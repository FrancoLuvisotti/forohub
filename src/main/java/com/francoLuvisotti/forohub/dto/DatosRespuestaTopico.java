package com.francoLuvisotti.forohub.dto;

public record DatosRespuestaTopico(
        String mensaje,
        boolean exito,
        Object datos
) {
}

