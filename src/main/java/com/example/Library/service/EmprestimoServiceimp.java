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
import java.util.Optional;

import static com.example.Library.repository.LivroRepository.*;

@Service
public class EmprestimoServiceimp implements EmprestimoService {

    @Autowired
    private EmprestimoServiceimp emprestimoService;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    private LivroServiceImp livroService;

    private void realizarEmprestimo() {
        System.out.println("ID do Usuário:");
        Long idUsuario = scanner.nextLong();
        scanner.nextLine();

        System.out.println("ID do Livro:");
        Long idLivro = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer

        try {
            Usuario usuario = usuarioService.buscarPorId(idUsuario)
                    .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado!"));
            Livro livro = livroService.buscarPorId(idLivro)
                    .orElseThrow(() -> new NoSuchElementException("Livro não encontrado!"));

            emprestimoService.realizarEmprestimo(usuario, livro);
            System.out.println("Empréstimo realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    @Override
    public void realizarDevolucao(Emprestimo emprestimo){
        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        emprestimo.setDataEvolucao(LocalDate.now());

        emprestimoRepository.save(emprestimo);
        LivroRepository.save(livro);


    }


    @Override
    public List<Emprestimo> listarTodos(){
        return emprestimoRepository.findAll();
    }

    @Override
    public Optional<Emprestimo> buscarPorId(Long id){
        return emprestimoRepository.findById(id);
    }


}
