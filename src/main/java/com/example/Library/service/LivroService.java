package com.example.Library.service;

import com.example.Library.model.Livro;
import com.example.Library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id){
        return livroRepository.findById(id);
    }

    public boolean emprestarLivro(Long id){
        Optional<Livro> optionalLivro = livroRepository.findById(id);

        if(optionalLivro.isPresent()){
            Livro livro = optionalLivro.get();
            if(livro.getQuantidadeDisponivel() > 0){
                livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
                livroRepository.save(livro);
                return true;
            }else {
                return false;
            }
        }

        return false;
    }


    public boolean devolverLivro(Long id){
        Optional<Livro> optionalLivro = livroRepository.findById(id);

        if(optionalLivro.isPresent()){
            Livro livro = optionalLivro.get();
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
            livroRepository.save(livro);
            return true;
        }
        return false;
    }
}
