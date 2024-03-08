package com.spring.start.usuarios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAO extends CrudRepository<Usuario, Long>{
    Optional<Usuario> findByNombre(String nombre);
}
