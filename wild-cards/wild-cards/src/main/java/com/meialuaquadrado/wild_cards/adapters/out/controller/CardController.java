package com.meialuaquadrado.wild_cards.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meialuaquadrado.wild_cards.adapters.in.Card;
import com.meialuaquadrado.wild_cards.adapters.in.Deck;
import com.meialuaquadrado.wild_cards.adapters.repositories.CardRepository;
import com.meialuaquadrado.wild_cards.adapters.repositories.DeckRepository;
import com.meialuaquadrado.wild_cards.model.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DeckRepository deckRepository;

    // http://localhost:8080/cards/buscarTodos
    @GetMapping("/buscarTodos")
    public ResponseEntity<String> getCards()  throws JsonProcessingException {
        List<Card> cards = cardRepository.findAll();
        if (!cards.isEmpty()) {
            return ResponseEntity.ok().body(cards.toString()); // Retorna a lista de cards
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum card encontrado");
        }

    }

    //    http://localhost:8080/cards/buscarPorId/1
    @GetMapping("/buscarPorId/{idCard}")
    public ResponseEntity<String> getCardById(@PathVariable("idCard") Integer idCard) throws JsonProcessingException {

        Card card = cardRepository.findById(idCard).orElse(null);

        try {

            if (card == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não encontrado");
            } else {
                return ResponseEntity.ok(objectMapper.writeValueAsString(card));// Retorna o card
            }
        } catch (JsonProcessingException e) {
            // Em caso de erro na serialização, retornar status 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar JSON");
        }
    }

        // http://localhost:8080/cards/buscarPorPerguntaOuResposta/{termo}
        @GetMapping("/buscarPorPerguntaOuResposta/{termo}")
        public ResponseEntity<String> getCardsByPerguntaOuResposta(@PathVariable("termo") String termo) throws JsonProcessingException {
            // Buscar todos os cards que correspondem à pergunta
            List<Card> cardsByPergunta = cardRepository.findAllByPerguntaContaining(termo);

            // Buscar todos os cards que correspondem à resposta
            List<Card> cardsByResposta = cardRepository.findAllByRespostaContaining(termo);

            // Combinar as duas listas sem duplicatas
            List<Card> combinedCards = new ArrayList<>(cardsByPergunta);
            for (Card card : cardsByResposta) {
                if (!combinedCards.contains(card)) {
                    combinedCards.add(card);
                }
            }

            if (!combinedCards.isEmpty()) {
                return ResponseEntity.ok(objectMapper.writeValueAsString(combinedCards)); // Retorna a lista de cards encontrados
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum card encontrado com a pergunta ou resposta fornecida.");
            }
        }

    // http://localhost:8080/cards/inserir
    @PostMapping("/inserirCard")
    public ResponseEntity<String> criarCard(@RequestBody CardDTO cardDTO) {
        // Verificar se o deck associado existe
        Optional<Deck> deckOpt = deckRepository.findById(cardDTO.getIdDeckFk());
        if (!deckOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck não encontrado.");
        }

        Optional<Card> cardExistente = cardRepository.findByPergunta(cardDTO.getPergunta());

        if (cardExistente.isPresent() && cardExistente.get().getIdDeckFk().getIdDeck() == cardDTO.getIdDeckFk()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um Card com essa pergunta neste Deck.");
        }

        // Converter CardDTO em entidade Card
        Card card = new Card();
        card.setPergunta(cardDTO.getPergunta());
        card.setResposta(cardDTO.getResposta());
        card.setIdDeckFk(deckOpt.get()); // Associar o deck

        // Salvar o novo card no repositório
        cardRepository.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card criado com sucesso.");
    }


//    // http://localhost:8080/cards/deletarPorPergunta/{pergunta}
//    @DeleteMapping("/deletarPorPergunta/{pergunta}")
//    public ResponseEntity<String> deletarPorPergunta(@PathVariable("pergunta") String pergunta) {
//        // Verifica se existe um card com a pergunta fornecida
//        Optional<Card> cardExistente = cardRepository.findByPergunta(pergunta);
//        if (cardExistente.isPresent()) {
//            cardRepository.deleteByPergunta(pergunta);
//            return ResponseEntity.ok("Card deletado com sucesso pela pergunta.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum card encontrado com essa pergunta.");
//        }
//    }
//
//
//    // http://localhost:8080/cards/deletarPorResposta/{resposta}
//    @DeleteMapping("/deletarPorResposta/{resposta}")
//    public ResponseEntity<String> deletarPorResposta(@PathVariable("resposta") String resposta) {
//        // Verifica se existe um card com a resposta fornecida
//        Optional<Card> cardExistente = cardRepository.findByResposta(resposta);
//        if (cardExistente.isPresent()) {
//            cardRepository.deleteByResposta(resposta);
//            return ResponseEntity.ok("Card deletado com sucesso pela resposta.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum card encontrado com essa resposta.");
//        }
//    }

    // http://localhost:8080/cards/deletarPorId/{idCard}
    @DeleteMapping("/deletarPorId/{idCard}")
    public ResponseEntity<String> deletarPorId(@PathVariable("idCard") Integer idCard) {
        // Verifica se existe um card com o ID fornecido
        Optional<Card> cardExistente = cardRepository.findById(idCard);
        if (cardExistente.isPresent()) {
            cardRepository.deleteById(idCard);
            return ResponseEntity.ok("Card deletado com sucesso pelo ID.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum card encontrado com esse ID.");
        }
    }

}

