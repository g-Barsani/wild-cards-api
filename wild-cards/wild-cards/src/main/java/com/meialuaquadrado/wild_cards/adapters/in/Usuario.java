package com.meialuaquadrado.wild_cards.adapters.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity  // Serve para utilizar tags de bancos de dados
@Table (name = "tab_usuario")  // Nomeia tabela
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)  // Permite o auto increment do ID no banco
    @Column (name="id_usuario")
    private int idUsuario;

    @Column (name="nome", length = 255, nullable = false)
    private String nome;
    @Column (name="email", length = 255, nullable = false)
    private String email;

    @Column (name="senha", length = 20, nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")  //
    private List<DeckUsuario> deckUsuarios;

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", deckUsuarios=" + deckUsuarios +
                '}';
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<DeckUsuario> getDeckUsuarios() {
        return deckUsuarios;
    }

    public void setDeckUsuarios(List<DeckUsuario> deckUsuarios) {
        this.deckUsuarios = deckUsuarios;
    }
}
