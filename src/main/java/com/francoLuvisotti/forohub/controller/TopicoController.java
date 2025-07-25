package com.francoLuvisotti.forohub.controller;

import com.francoLuvisotti.forohub.dto.*;
import com.francoLuvisotti.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<ApiResponse<DetalleTopicoDTO>> registrar(
          @RequestBody @Valid RegistroTopicoDTO datos) {

          DetalleTopicoDTO respuesta = topicoService.registrar(datos);

          return ResponseEntity.status(HttpStatus.CREATED).body(
          new ApiResponse<>(true, "Tópico creado correctamente", respuesta)
    );
}

    @GetMapping
    public ResponseEntity<DatosRespuestaTopico> listar() {
        var lista = topicoService.listar();
        return ResponseEntity.ok(new DatosRespuestaTopico("Listado de tópicos obtenido correctamente", true, lista));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetalleTopicoDTO>> detalle(@PathVariable Long id) {
        DetalleTopicoDTO detalle = topicoService.detalle(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Detalle obtenido correctamente", detalle));
}

   @PutMapping("/{id}")
public ResponseEntity<ApiResponse<DetalleTopicoDTO>> actualizar(
        @PathVariable Long id,
        @RequestBody @Valid ActualizacionTopicoDTO datos) {

    DetalleTopicoDTO actualizado = topicoService.actualizar(id, datos);

    return ResponseEntity.ok(new ApiResponse<>(true, "Tópico actualizado correctamente", actualizado));
}

    @DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
    topicoService.eliminar(id);
    return ResponseEntity.ok(new ApiResponse<>(true, "Tópico eliminado correctamente", null));
}
}
