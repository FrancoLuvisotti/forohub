package com.francoLuvisotti.forohub.repository;

import com.francoLuvisotti.forohub.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
