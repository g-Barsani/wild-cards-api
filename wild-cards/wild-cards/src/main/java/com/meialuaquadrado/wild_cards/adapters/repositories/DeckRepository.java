package com.meialuaquadrado.wild_cards.adapters.repositories;

import com.meialuaquadrado.wild_cards.adapters.in.Deck;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
    List<Deck> findAll();

    Optional<Deck> findByIdDeck(Integer id);
    Optional<Deck> findByNome (String nome);
    @Query("SELECT d FROM Deck d WHERE d.idUsuarioFk.idUsuario = :idUsuario")
    List<Deck> findByIdUsuarioFk(@Param("idUsuario") Integer idUsuario);

    @Override
    void deleteById(Integer id);

    void deleteByNome(String nome);

}
