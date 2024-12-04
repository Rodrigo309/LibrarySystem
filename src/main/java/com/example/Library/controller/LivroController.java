package com.example.Library.controller;

import com.example.Library.model.Livro;
import com.example.Library.service.LivroServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroServiceImp livroService;

    @PostMapping
    public Livro cadastrar(@RequestBody Livro livro){
        return livroService.salvar(livro);
    }

    public List<Livro> listar(){
        return livroService.listarTodos();
    }
}
