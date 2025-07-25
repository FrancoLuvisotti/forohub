package com.francoLuvisotti.forohub.service;

import com.francoLuvisotti.forohub.domain.topico.Topico;
import com.francoLuvisotti.forohub.domain.topico.Topico.StatusTopico;
import com.francoLuvisotti.forohub.dto.*;
import com.francoLuvisotti.forohub.mapper.TopicoMapper;
import com.francoLuvisotti.forohub.domain.curso.CursoRepository;
import com.francoLuvisotti.forohub.domain.topico.TopicoRepository;
import com.francoLuvisotti.forohub.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicoService {

    @Autowired private TopicoRepository topicoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CursoRepository cursoRepository;

    @Transactional
    public DetalleTopicoDTO registrar(RegistroTopicoDTO dto) {
        var autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));

        var curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));


        var topico = Topico.builder()
                .titulo(dto.titulo())
                .mensaje(dto.mensaje())
                .fechaCreacion(LocalDateTime.now())
                .status(StatusTopico.ABIERTO)
                .autor(autor)
                .curso(curso)
                .build();

        topicoRepository.save(topico);
        return TopicoMapper.toDetalleDTO(topico);
    }

    public List<ListadoTopicoDTO> listar() {
        return topicoRepository.findAll()
                .stream()
                .map(TopicoMapper::toListarDTO)
                .toList();
    }

    public DetalleTopicoDTO detalle(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico con ID " + id + " no encontrado"));
        return TopicoMapper.toDetalleDTO(topico);
    }

    @Transactional
    public DetalleTopicoDTO actualizar(Long id, ActualizacionTopicoDTO dto) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        topico.setTitulo(dto.titulo());
        topico.setMensaje(dto.mensaje());

        return TopicoMapper.toDetalleDTO(topico);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }
}
