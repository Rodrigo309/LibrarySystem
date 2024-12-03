package com.example.Library.main;

import com.example.Library.model.Livro;
import com.example.Library.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main {

    @Autowired
    private LivroService livroService;
    Scanner scanner = new Scanner(System.in);

    @EventListener(ApplicationReadyEvent.class)
    public void iniciar(){

        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1 -> cadastrarLivro();
                case 2 -> listarLivros();
                case 3 -> realizarEmprestimo();
                case 4 -> registrarDevolucao();
                case 0 -> System.out.println("Saindo do sistema. . .");
                default -> System.out.println("Opcao invalida");
            }


        }while (opcao != 0);
    }

    private void exibirMenu(){
        System.out.println("=== Sistema de Biblioteca ===");
        System.out.println("1 - Cadastrar Livro");
        System.out.println("2 - Listar Livros");
        System.out.println("3 - Realizar Empréstimo");
        System.out.println("4 - Registrar Devolução");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarLivro(){
        System.out.println("=== Cadastro de Livro ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Quantidade Disponível: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setQuantidadeDisponivel(quantidade);

        livroService.salvar(livro);

        System.out.println("Livro cadastrado com sucesso!");
    }

    private void listarLivros(){
        System.out.println("=== Lista de Livros ===");
        livroService.listarTodos().forEach(livro ->
                System.out.println(livro.getId() + " - " + livro.getTitulo() + " por " + livro.getAutor())
        );
    }

    private void realizarEmprestimo(){
        System.out.println("=== Realizar Empréstimo ===");
        System.out.print("ID do Livro: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        boolean sucesso = livroService.emprestarLivro(id);

        if(sucesso){
            System.out.println("Empréstimo realizado com sucesso!");
        }else{
            System.out.println("Não foi possível realizar o empréstimo. Verifique o ID ou a disponibilidade.");
        }
    }

    private void registrarDevolucao(){
        System.out.println("=== Registrar Devolução ===");
        System.out.print("ID do Livro: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        boolean sucesso = livroService.devolverLivro(id);

        if (sucesso) {
            System.out.println("Devolução registrada com sucesso!");
        } else {
            System.out.println("Não foi possível registrar a devolução. Verifique o ID.");
        }
    }
}
