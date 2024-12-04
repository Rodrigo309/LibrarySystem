package com.example.Library.service;

import com.example.Library.model.Livro;

import java.util.Optional;

public interface LivroService {
    Optional<Livro> buscarPorId(Long id);
}
