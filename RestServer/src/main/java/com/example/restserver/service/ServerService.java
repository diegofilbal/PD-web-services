package com.example.restserver.service;

import com.example.restserver.model.Administrador;
import com.example.restserver.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServerService {

    // Lista de Usuarios
    private List<Usuario> usuarios = new ArrayList<>();

    // Lista de Objetos
    private ArrayList<String> objetos = new ArrayList<>();


    public ServerService() {
        usuarios.add(new Administrador("admin", "admin", true, true));
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
                System.out.println("[" + new Date() + "] Usuario \"" + nome + "\" registrado com sucesso!");
                return true;
            }
            System.out.println("[" + new Date() + "] Usuario \"" + nome + "\" ja existe!");
            return false;

        } catch (Exception e) {
            System.out.println("[" + new Date() + "] Erro inesperado ao registrar usuario \"" + nome + "\" na classe cliente");
            e.printStackTrace();
            return false;
        }
    }

    // Metodo que autentica um usuario e o autoriza a operar um objeto
    public String solicitaAcesso(String nome, String senha, String objeto, String operacao) {
        String msg;
        try {
            // Verifica se o usuario existe e se a senha esta correta
            Usuario usuario = autentica(nome, senha);
            if (usuario != null) {
                if (operacao.equals("ler") && usuario.isPodeLer()) {
                    if (!objetos.contains(objeto)) {
                        msg = "Objeto \"" + objeto + "\" nao existe!";
                        System.out.println("[" + new Date() + "] " + msg);
                        return msg;
                    }
                    msg = "Acesso concedido!";
                    System.out.println("[" + new Date() + "] " + msg);
                    return msg;

                } else if (operacao.equals("escrever") && usuario.isPodeEscrever()) {
                    if (!objetos.contains(objeto)) {
                        objetos.add(objeto);
                        msg = "Objeto \"" + objeto + "\" criado!";
                        System.out.println("[" + new Date() + "] " + msg);
                        return msg;
                    }
                    msg = "Acesso concedido!";
                    System.out.println("[" + new Date() + "] " + msg);
                    return msg;
                }
                msg = "Acesso negado!";
                System.out.println("[" + new Date() + "] " + msg);
                return msg;
            }
            msg = "Usuario nao encontrado!";
            System.out.println("[" + new Date() + "] " + msg);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Erro ao solicitar acesso!";
            System.out.println("[" + new Date() + "] " + msg);
            return msg;
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

    private Usuario getUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }
}