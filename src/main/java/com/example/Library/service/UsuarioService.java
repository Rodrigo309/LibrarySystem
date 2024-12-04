package com.example.Library.service;

import com.example.Library.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
}
