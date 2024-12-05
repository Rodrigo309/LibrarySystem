package com.example.Library.service;

import com.example.Library.model.Emprestimo;
import com.example.Library.model.Livro;
import com.example.Library.model.Usuario;
import com.example.Library.repository.EmprestimoRepository;
import com.example.Library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmprestimoServiceimp  implements  EmprestimoService{
    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    private LivroServiceImp livroService;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Override
    public void realizarEmprestimo(Usuario usuario, Livro livro) {
        try {
            Usuario usuarioEncontrado = usuarioService.buscarPorId(usuario.getId())
                    .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado!"));
            Livro livroEncontrado = livroService.buscarPorId(livro.getId())
                    .orElseThrow(() -> new NoSuchElementException("Livro não encontrado!"));

            if(livroEncontrado.getQuantidadeDisponivel() <= 0){
                throw new IllegalStateException("Livro disponivel para empréstimo.");
            }

            livroEncontrado.setQuantidadeDisponivel(livroEncontrado.getQuantidadeDisponivel() - 1);

            Emprestimo novoEmprestimo = new Emprestimo();
            novoEmprestimo.setUsuario(usuarioEncontrado);
            novoEmprestimo.setLivro(livroEncontrado);
            novoEmprestimo.setDataEmprestimo(LocalDate.now());

            emprestimoRepository.save(novoEmprestimo);
            livroRepository.save(livroEncontrado);

            System.out.println("Empréstimo realizado com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    @Override
    public void realizarDevolucao(Emprestimo emprestimo) {
        try {
            Livro livro = emprestimo.getLivro();
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
            emprestimo.setDataDevolucao(LocalDate.now());

            emprestimoRepository.save(emprestimo);
            livroRepository.save(livro);
            System.out.println("Devolução realizada com sucesso!");

        }catch (Exception e){
            System.out.println("Erro ao realizar devolução: " + e.getMessage());
        }
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    @Override
    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }
}
