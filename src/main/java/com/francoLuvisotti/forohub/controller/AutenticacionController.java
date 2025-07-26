package com.francoLuvisotti.forohub.controller;

import com.francoLuvisotti.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.francoLuvisotti.forohub.domain.usuario.DatosRegistroUsuario;
import com.francoLuvisotti.forohub.domain.usuario.Usuario;
import com.francoLuvisotti.forohub.domain.usuario.UsuarioRepository;
import com.francoLuvisotti.forohub.infra.security.DatosJWTToken;
import com.francoLuvisotti.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(),
                datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @RestController
    @RequestMapping("/usuarios")
    public class UsuarioController {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostMapping
        public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroUsuario datos) {
            if (usuarioRepository.findByEmail(datos.email()).isPresent()) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }

            var usuario = new Usuario(
                    null,
                    datos.nombre(),
                    datos.email(),
                    passwordEncoder.encode(datos.contrasena())
            );
            usuarioRepository.save(usuario);

            return ResponseEntity.ok().build();
        }

    }

}

