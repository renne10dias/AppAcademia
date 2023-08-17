package com.projetoslabex.model;

public class LoginUsuario {
    private String nome;
    private String senha;

    public LoginUsuario() {
    }

    public LoginUsuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
