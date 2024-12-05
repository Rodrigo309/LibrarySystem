package com.example.Library.repository;

import com.example.Library.model.Emprestimo;
import com.example.Library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {


}
