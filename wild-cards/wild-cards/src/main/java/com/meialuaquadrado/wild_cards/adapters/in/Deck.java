package com.meialuaquadrado.wild_cards.adapters.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tab_deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deck")
    private int idDeck;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_usuario", nullable = false)
    private Usuario idUsuarioFk;

//    @ManyToOne
//    @JoinColumn(referencedColumnName = "id_usuario", nullable = false)
//    private Usuario usuario;


    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @Override
    public String toString() {
        return "Deck{" +
                "idDeck=" + idDeck +
                ", idUsuarioFk=" + idUsuarioFk +
                ", nome='" + nome + '\'' +
                '}';
    }

    public int getIdDeck() {
        return idDeck;
    }

    public void setIdDeck(int idDeck) {
        this.idDeck = idDeck;
    }

    public Usuario getIdUsuarioFk() {
        return idUsuarioFk;
    }

    public void setIdUsuarioFk(Usuario idUsuarioFk) {
        this.idUsuarioFk = idUsuarioFk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



}
