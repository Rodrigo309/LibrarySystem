package com.example.Library.service;

import com.example.Library.model.Emprestimo;
import com.example.Library.model.Livro;
import com.example.Library.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface EmprestimoService {
    void realizarEmprestimo(Usuario usuario, Livro livro);
    void realizarDevolucao(Emprestimo emprestimo);

    List<Emprestimo> listarTodos();
    Optional<Emprestimo> buscarPorId(Long id);


}
