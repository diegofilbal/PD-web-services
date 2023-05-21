package com.example.restserver.controller;

import com.example.restserver.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping("/registra-usuario")
    public boolean registraUsuario(@RequestParam String nome, @RequestParam String senha, @RequestParam boolean podeLer,
                                   @RequestParam boolean podeEscrever, @RequestParam boolean ehAdmin) {
        return serverService.registraUsuario(nome, senha, podeLer, podeEscrever, ehAdmin);
    }

    @GetMapping("/solicita-acesso")
    public String solicitaAcesso(@RequestParam String nome, @RequestParam String senha, @RequestParam String objeto,
                                 @RequestParam String operacao) {
        return serverService.solicitaAcesso(nome, senha, objeto, operacao);
    }

    @GetMapping("/eh-admin")
    public boolean ehAdmin(@RequestParam String nome, @RequestParam String senha) {
        return serverService.ehAdmin(nome, senha);
    }

}
