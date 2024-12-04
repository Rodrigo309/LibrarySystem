package com.example.Library.service;

import com.example.Library.model.Usuario;
import com.example.Library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UsuarioServiceImp implements UsuarioService {
        @Autowired
        private UsuarioRepository usuarioRepository;

        @Override
        public Usuario salvar(Usuario usuario){
            return usuarioRepository.save(usuario);
        }

        @Override
        public Optional<Usuario> buscarPorId(Long id){
            return usuarioRepository.findById(id);
        }
}
