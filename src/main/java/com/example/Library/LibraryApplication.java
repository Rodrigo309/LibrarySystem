package com.example.Library;

import com.example.Library.model.Emprestimo;
import com.example.Library.model.Livro;
import com.example.Library.model.Usuario;
import com.example.Library.service.EmprestimoServiceimp;
import com.example.Library.service.LivroServiceImp;
import com.example.Library.service.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LibraryApplication {
	@Autowired
	private EmprestimoServiceimp emprestimoService;

	@Autowired
	private UsuarioServiceImp usuarioService;

	@Autowired
	private LivroServiceImp livroService;

	private final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> iniciar(); // Chama o método iniciar
	}

	private int lerOpcaoMenu() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine()); // Converte a entrada para um inteiro
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida, por favor insira um número.");
			}
		}
	}

	public void iniciar() {
		System.out.println("Aplicação pronta! Iniciando menu...");
		int opcao;

		do {
			System.out.println("1 - Cadastrar Usuário");
			System.out.println("2 - Realizar Empréstimo");
			System.out.println("3 - Realizar Devolução");
			System.out.println("4 - Listar Empréstimos");
			System.out.println("0 - Sair");

			opcao = lerOpcaoMenu(); // Utiliza a nova função para ler a opção

			switch (opcao) {
				case 1 -> cadastrarUsuario();
				case 2 -> realizarEmprestimo();
				case 3 -> realizarDevolucao();
				case 4 -> listarEmprestimos();
				case 0 -> System.out.println("Saindo do sistema...");
				default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 0);
	}

	private void cadastrarUsuario() {
		System.out.println("Nome do Usuário:");
		String nome = scanner.nextLine();
		System.out.println("E-mail do Usuário:");
		String email = scanner.nextLine();
		System.out.println("CPF:");
		String cpf = scanner.nextLine();

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setCpf(cpf);

		usuarioService.salvar(usuario);
		System.out.println("Usuário cadastrado com sucesso!");
	}

	private void realizarEmprestimo() {
		System.out.println("ID do Usuário:");
		Long idUsuario = scanner.nextLong();
		scanner.nextLine();

		System.out.println("ID do Livro:");
		Long idLivro = scanner.nextLong();
		scanner.nextLine();

		try {
			Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
			Optional<Livro> livro = livroService.buscarPorId(idLivro);
			emprestimoService.realizarEmprestimo(usuario.orElse(null), livro.orElse(null));
			System.out.println("Empréstimo realizado com sucesso!");
		} catch (Exception e) {
			System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
		}
	}

	private void realizarDevolucao() {
		System.out.println("ID do Empréstimo:");
		Long idEmprestimo = scanner.nextLong();
		scanner.nextLine();

		try {
			Emprestimo emprestimo = emprestimoService.buscarPorId(idEmprestimo)
					.orElseThrow(() -> new NoSuchElementException("Empréstimo não encontrado para o ID: " + idEmprestimo));
			emprestimoService.realizarDevolucao(emprestimo);
			System.out.println("Devolução registrada com sucesso!");
		} catch (Exception e) {
			System.out.println("Erro ao registrar devolução: " + e.getMessage());
		}
	}

	private void listarEmprestimos() {
		emprestimoService.listarTodos().forEach(emprestimo -> {
			System.out.println("Empréstimo ID: " + emprestimo.getId());
			System.out.println("Usuário: " + emprestimo.getUsuario().getNome());
			System.out.println("Livro: " + emprestimo.getLivro().getTitulo());
			System.out.println("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
			System.out.println("Data de Devolução: " + (emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao() : "Não devolvido"));
			System.out.println("-----------------------------------");
		});
	}
}
