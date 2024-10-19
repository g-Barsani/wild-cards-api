package com.meialuaquadrado.wild_cards.model;

public class DeckUsuarioDTO {
    private int idUsuarioFk;
    private int idDeckFk;

    // Getters e setters

    public int getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(int idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public int getIdDeckFk() {
        return idDeckFk;
    }

    public void setIdDeckFk(int idDeckFk) {
        this.idDeckFk = idDeckFk;
    }
}
