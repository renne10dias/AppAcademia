package com.projetoslabex.model;

public class UsuarioLogadoResponse {
    private String nome;
    private String cpf;
    private String usuario_type;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuario_type() {
        return usuario_type;
    }

    public void setUsuario_type(String usuario_type) {
        this.usuario_type = usuario_type;
    }
}
