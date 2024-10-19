package com.meialuaquadrado.wild_cards.adapters.repositories;

import com.meialuaquadrado.wild_cards.adapters.in.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findAll();

    Optional<Card> findByIdCard(Integer id);

    Optional<Card> findByPergunta(String pergunta);
    Optional<Card> findByResposta(String resposta);

    List<Card> findAllByPerguntaContaining(String termo);

    List<Card> findAllByRespostaContaining(String termo);

    @Override
    void deleteById(Integer id);

//    void deleteByPergunta(String pergunta);
//    void deleteByResposta(String resposta);

}
