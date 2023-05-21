package com.example.restserver.model;

public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nome, String senha, boolean podeLer, boolean podeEscrever) {
        super(nome, senha, podeLer, podeEscrever);
    }
}
