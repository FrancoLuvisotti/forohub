package com.francoLuvisotti.forohub.infra.security;

import com.francoLuvisotti.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.francoLuvisotti.forohub.domain.usuario.Usuario;
import com.francoLuvisotti.forohub.domain.usuario.UsuarioRepository;
import com.francoLuvisotti.forohub.domain.usuario.DatosJWTToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacionService(AuthenticationManager authenticationManager,
                                TokenService tokenService,
                                UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    public DatosJWTToken autenticar(DatosAutenticacionUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datos.email(), datos.contrasena());

        Authentication authResult = authenticationManager.authenticate(authToken);

        Usuario usuario = (Usuario) authResult.getPrincipal();

        String jwtToken = tokenService.generarToken(usuario);

        return new DatosJWTToken(jwtToken);
    }
}
