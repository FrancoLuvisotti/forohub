package com.francoLuvisotti.forohub.controller;

import com.francoLuvisotti.forohub.domain.usuario.*;
import com.francoLuvisotti.forohub.dto.ApiResponse;
import com.francoLuvisotti.forohub.infra.security.AutenticacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionController(AutenticacionService autenticacionService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.autenticacionService = autenticacionService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<DatosJWTToken>> login(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        var token = autenticacionService.autenticar(datos);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login exitoso", token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<String>> registrar(@RequestBody @Valid DatosRegistroUsuario datos) {
        if (usuarioRepository.findByEmail(datos.email()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El usuario ya existe", null));
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datos.nombre());
        nuevoUsuario.setEmail(datos.email());
        nuevoUsuario.setClave(passwordEncoder.encode(datos.contrasena()));
        nuevoUsuario.setPerfil(datos.perfil());

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario registrado correctamente", null));
    }
}

