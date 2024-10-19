package com.meialuaquadrado.wild_cards.model;

public class DeckDTO {
    private int idUsuarioFk;
    private String nome;

    // Getters e setters
    public int getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(int idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
