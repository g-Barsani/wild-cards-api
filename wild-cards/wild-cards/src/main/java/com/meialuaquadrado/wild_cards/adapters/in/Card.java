package com.meialuaquadrado.wild_cards.adapters.in;

import jakarta.persistence.*;

@Entity
@Table(name = "tab_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private int idCard;

    @Column(name = "pergunta", length = 500, nullable = false)
    private String pergunta;

    @Column(name = "resposta", length = 500, nullable = false)
    private String resposta;

//     Várias cartas para um deck
    @ManyToOne
    @JoinColumn(referencedColumnName ="id_deck", nullable = false)
    private Deck idDeckFk;

    @Override
    public String toString() {
        return "Card{" +
                "idCard=" + idCard +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                ", idDeckFk=" + idDeckFk +
                '}';
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Deck getIdDeckFk() {
        return idDeckFk;
    }

    public void setIdDeckFk(Deck idDeckFk) {
        this.idDeckFk = idDeckFk;
    }
}