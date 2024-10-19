package com.meialuaquadrado.wild_cards.model;

public class CardDTO {
    private String pergunta;
    private String resposta;
    private int idDeckFk;

    public CardDTO() {
    }

    public CardDTO(String pergunta, String resposta, int idDeckFk) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.idDeckFk = idDeckFk;
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                ", idDeckFk=" + idDeckFk +
                '}';
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

    public int getIdDeckFk() {
        return idDeckFk;
    }

    public void setIdDeckFk(int idDeckFk) {
        this.idDeckFk = idDeckFk;
    }
}
