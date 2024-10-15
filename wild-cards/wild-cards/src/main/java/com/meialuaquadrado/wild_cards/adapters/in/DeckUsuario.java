package com.meialuaquadrado.wild_cards.adapters.in;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tab_deck_usuario")
public class DeckUsuario {
//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deck_usuario")
    private int idDeckUsuario;

    @ManyToOne
    @JoinColumn(name="id_usuario_fk", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="id_deck_fk", referencedColumnName = "id_deck", nullable = false)
    private Deck deck;

    @Override
    public String toString() {
        return "DeckUsuario{" +
                "idDeckUsuario=" + idDeckUsuario +
                ", usuario=" + usuario +
                ", deck=" + deck +
                '}';
    }

    public int getIdDeckUsuario() {
        return idDeckUsuario;
    }

    public void setIdDeckUsuario(int idDeckUsuario) {
        this.idDeckUsuario = idDeckUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}