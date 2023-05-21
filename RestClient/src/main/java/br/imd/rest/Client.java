package br.imd.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.imd.rest.exceptions.RestRequestException;

public class Client {

	public Client() {
	}

	public boolean registraUsuario(String nome, String senha, boolean podeLer, boolean podeEscrever, boolean ehAdmin)
			throws RestRequestException {

		String uri = "http://localhost:8080/api/server/registra-usuario"
				+ "?nome=" + nome + "&senha=" + senha + "&podeLer=" + podeLer
				+ "&podeEscrever=" + podeEscrever + "&ehAdmin=" + ehAdmin;
		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("accept", "application/json");

		String response = HttpUtils.httpPostRequest(uri, headerParams, null, 200);

		return response.equals("true");
	}
	
	public String solicitaAcesso(String nome, String senha, String objeto, String operacao) throws RestRequestException {

		String uri = "http://localhost:8080/api/server/solicita-acesso"
				+ "?nome=" + nome + "&senha=" + senha + "&objeto=" + objeto
				+ "&operacao=" + operacao;
		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("accept", "application/json");

		return HttpUtils.httpGetRequest(uri, headerParams);
	}
	
	
	public boolean ehAdmin(String nome, String senha) throws RestRequestException {

		String uri = "http://localhost:8080/api/server/eh-admin"
				+ "?nome=" + nome + "&senha=" + senha;
		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("accept", "application/json");

		String response = HttpUtils.httpGetRequest(uri, headerParams);

		return response.equals("true");
	}
	
	

	public static void main(String[] args) throws RestRequestException {

		Scanner sc = new Scanner(System.in);
		Client restClient = new Client();
		int op;

		do {
			System.out.println("\n------------- MENU -------------");
			System.out.println("1 - Registrar novo usuario");
			System.out.println("2 - Solicitar acesso a um objeto");
			System.out.println("3 - Sair");
			System.out.println("--------------------------------");
			System.out.print("\nDigite sua opcao: ");
			op = sc.nextInt();
			sc.nextLine();

			switch (op){
				case 1:
					System.out.print("\nDigite o seu nome: ");
					String nomeCriador = sc.nextLine();
					System.out.print("\nDigite a sua senha: ");
					String senhaCriador = sc.nextLine();
					if (restClient.ehAdmin(nomeCriador, senhaCriador)) {
						System.out.print("\nDigite o nome do usuario a ser registrado: ");
						String nome = sc.nextLine();
						System.out.print("\nDigite a senha do usuario a ser registrado: ");
						String senha = sc.nextLine();
						System.out.print("\nO novo usuario tera permissao de leitura? (s/n): ");
						String podeLer = sc.nextLine();
						System.out.print("\nO novo usuario tera permissao de escrita? (s/n): ");
						String podeEscrever = sc.nextLine();
						System.out.print("\nO novo usuario sera administrador? (s/n): ");
						String ehAdmin = sc.nextLine();
						if (restClient.registraUsuario( nome, senha, podeLer.equals("s"), podeEscrever.equals("s"), ehAdmin.equals("s"))) {
							System.out.println("\nUsuario registrado com sucesso!");
						} else {
							System.out.println("\nErro ao registrar usuario!");
						}
					} else {
						System.out.println("\nVoce nao tem permissao para registrar usuarios!");
					}
					break;

				case 2:
					System.out.print("\nDigite o seu nome: ");
					String nomeUsuario = sc.nextLine();
					System.out.print("\nDigite a sua senha: ");
					String senhaUsuario = sc.nextLine();
					System.out.print("\nDigite o nome do objeto que deseja acessar: ");
					String nomeObjeto = sc.nextLine();
					System.out.print("\nDigite a operacao que deseja realizar (1 - Ler | 2 - Escrever): ");
					String operacao = sc.nextLine();
					if (operacao.equals("1")) {
						System.out.println("\n" + restClient.solicitaAcesso(nomeUsuario, senhaUsuario, nomeObjeto, "ler"));
					} else if (operacao.equals("2")) {
						System.out.println("\n" + restClient.solicitaAcesso(nomeUsuario, senhaUsuario, nomeObjeto, "escrever"));
					} else {
						System.out.println("\nOperacao invalida!");
						break;
					}
					break;

				case 3: break;

				default: System.out.println("\nOpcao invalida!");
			}
		}while (op != 3);

		System.out.println("\n\nFim do programa!");

	}

}
