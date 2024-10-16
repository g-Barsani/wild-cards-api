package com.meialuaquadrado.wild_cards.adapters.repositories;

import com.meialuaquadrado.wild_cards.adapters.in.Deck;
import com.meialuaquadrado.wild_cards.adapters.in.DeckUsuario;
import com.meialuaquadrado.wild_cards.adapters.in.Usuario;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckUsuarioRepository extends JpaRepository<DeckUsuario, Integer> {

    Optional<DeckUsuario> findByIdDeckUsuario(Integer id);

    @Override
    void deleteById(Integer id);

    List<DeckUsuario> findAll();

    // Método para buscar DeckUsuario por ID do usuário
    @Query("SELECT du FROM DeckUsuario du WHERE du.usuario.idUsuario = :idUsuario")
    Optional<DeckUsuario> findByUsuarioId(Integer idUsuario);

    // Método para buscar DeckUsuario por ID do deck
    @Query("SELECT du FROM DeckUsuario du WHERE du.deck.idDeck = :idDeck")
    Optional<DeckUsuario> findByDeckId(Integer idDeck);
}
