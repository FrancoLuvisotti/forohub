package com.francoLuvisotti.forohub.mapper;

import com.francoLuvisotti.forohub.domain.topico.Topico;
import com.francoLuvisotti.forohub.dto.*;

public class TopicoMapper {

    public static ListadoTopicoDTO toListarDTO(Topico topico) {
        return new ListadoTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getEmail(),
                topico.getCurso().getNombre()
        );
    }


    public static DetalleTopicoDTO toDetalleDTO(Topico topico) {
        return new DetalleTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().name(),
                topico.getAutor().getEmail(),
                topico.getCurso().getNombre()
        );
    }
}
