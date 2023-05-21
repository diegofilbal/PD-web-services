package com.example.restserver.service;

import com.example.restserver.model.Administrador;
import com.example.restserver.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerService {

    // Lista de Usuarios
    private List<Usuario> usuarios = new ArrayList<>();

    // Lista de Objetos
    private ArrayList<String> objetos = new ArrayList<>();


    public ServerService() {
    }

    // Metodo que cadastra novo usuario
    public boolean registraUsuario(String nome, String senha, boolean podeLer, boolean podeEscrever, boolean ehAdmin) {
        try {
            // Verifica se o usuario ja existe
            if (getUsuario(nome) == null) {
                // Verifica se o usuario eh administrador
                if (ehAdmin) {
                    usuarios.add(new Administrador(nome, senha, podeLer, podeEscrever));
                } else {
                    usuarios.add(new Usuario(nome, senha, podeLer, podeEscrever));
                }
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("Erro ao registrar usuario na classe cliente");
            e.printStackTrace();
            return false;
        }
    }

    // Metodo que autentica um usuario e o autoriza a operar um objeto
    public String solicitaAcesso(String nome, String senha, String objeto, String operacao) {
        try {
            // Verifica se o usuario existe e se a senha esta correta
            Usuario usuario = autentica(nome, senha);
            if (usuario != null) {
                if (operacao.equals("ler") && usuario.isPodeLer()) {
                    if (!objetos.contains(objeto)) {
                        return "Objeto nao existe!";
                    }
                    return "Acesso concedido!";

                } else if (operacao.equals("escrever") && usuario.isPodeEscrever()) {
                    if (!objetos.contains(objeto)) {
                        objetos.add(objeto);
                        return "Objeto criado!";
                    }
                    return "Acesso concedido!";
                }
                return "Acesso negado!";
            }
            return "Usuario nao encontrado!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao solicitar acesso!";
        }
    }

    public Usuario autentica (String nome, String senha) {
        // Verifica se o usuario existe e se a senha esta correta
        Usuario usuario = getUsuario(nome);
        if (usuario != null) {
            if (usuario.getSenha() != null && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean ehAdmin(String nome, String senha) {
        Usuario usuario = autentica(nome, senha);
        if (usuario != null) {
            return usuario instanceof Administrador;
        }
        return false;
    }

    public void listaUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("\nNenhum usuario cadastrado");
        } else {
            System.out.println();
            for (Usuario usuario : usuarios) {
                System.out.println("--------------------------------");
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Senha: " + usuario.getSenha());
                System.out.println("Leitura: " + usuario.isPodeLer());
                System.out.println("Escrita: " + usuario.isPodeEscrever());
                System.out.println("Eh Admin: " + (usuario instanceof Administrador));
            }
            System.out.println("--------------------------------\n");
        }
    }

    public void listaObjetos() {
        if (objetos.isEmpty()) {
            System.out.println("\nNenhum objeto cadastrado!");
        } else {
            System.out.println();
            for (String obj : objetos) {
                System.out.println("--------------------------------");
                System.out.println("Nome: " + obj);
            }
            System.out.println("--------------------------------\n");
        }
    }

    private Usuario getUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public void reset() {
        usuarios.clear();
        registraUsuario("admin", "admin", true, true, true);
        objetos.clear();
        System.out.println("\nUsuarios e objetos resetados!");
    }
}