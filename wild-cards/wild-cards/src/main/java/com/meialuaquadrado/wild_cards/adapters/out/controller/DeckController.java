package com.meialuaquadrado.wild_cards.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meialuaquadrado.wild_cards.adapters.in.Card;
import com.meialuaquadrado.wild_cards.adapters.in.Deck;
import com.meialuaquadrado.wild_cards.adapters.in.Usuario;
import com.meialuaquadrado.wild_cards.adapters.repositories.CardRepository;
import com.meialuaquadrado.wild_cards.adapters.repositories.DeckRepository;
import com.meialuaquadrado.wild_cards.adapters.repositories.UsuarioRepository;
import com.meialuaquadrado.wild_cards.model.DeckDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UsuarioRepository usuarioRepository; // Adicionado para verificar o usuário


    @Autowired
    ObjectMapper objectMapper;


    // http://localhost:8080/decks/buscarTodos
    @GetMapping("/buscarTodos")
    public ResponseEntity<String> getDecks()  throws JsonProcessingException {
        List<Deck> decks = deckRepository.findAll();
        if (!decks.isEmpty()) {
            return ResponseEntity.ok().body(decks.toString()); // Retorna a lista de decks
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum deck encontrado");
        }

    }


    // http://localhost:8080/decks/buscarPorUsuario/{idUsuario}
    @GetMapping("/buscarPorUsuario/{idUsuario}")
    public ResponseEntity<String> getDecksByUsuarioId(@PathVariable("idUsuario") Integer idUsuario) throws JsonProcessingException {
        List<Deck> decks = deckRepository.findByIdUsuarioFk(idUsuario);

        if (decks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum deck encontrado para este usuário.");
        } else {
            return ResponseEntity.ok(objectMapper.writeValueAsString(decks)); // Retorna a lista de decks como JSON
        }
    }

    // http://localhost:8080/decks/inserir
    @PostMapping("/inserirDeck")
    public ResponseEntity<String> criarDeck(@RequestBody DeckDTO novoDeckDTO) {
        // Verifica se um deck com o mesmo nome já existe
        if (deckRepository.findByNome(novoDeckDTO.getNome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um deck com esse nome.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdUsuario(novoDeckDTO.getIdUsuarioFk());

        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com o ID fornecido não encontrado.");
        }

        Usuario usuario = usuarioOpt.get(); // Extraindo o objeto Usuario do Optional

        // Cria um novo objeto Deck e associa o usuário encontrado
        Deck novoDeck = new Deck();
        novoDeck.setNome(novoDeckDTO.getNome());
        novoDeck.setIdUsuarioFk(usuario); // Associa o usuário pelo ID

        // Salva o novo deck no repositório
        deckRepository.save(novoDeck);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deck criado com sucesso.");
    }

    // http://localhost:8080/decks/buscarPorNome/{nome}
    @GetMapping("/buscarPorNome/{nome}")
    public ResponseEntity<String> buscarDeckPorNome(@PathVariable("nome") String nome) throws JsonProcessingException {
        Optional<Deck> deck = deckRepository.findByNome(nome);

        if (deck.isPresent()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(deck.get())); // Retorna o deck encontrado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck não encontrado");
        }
    }


    // http://localhost:8080/decks/buscarPorId/{idDeck}
    @GetMapping("/buscarPorId/{idDeck}")
    public ResponseEntity<String> buscarDeckPorId(@PathVariable("idDeck") Integer idDeck) throws JsonProcessingException {
        Optional<Deck> deck = deckRepository.findById(idDeck);

        if (deck.isPresent()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(deck.get())); // Retorna o deck encontrado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck não encontrado");
        }
    }

    // http://localhost:8080/decks/deletarPorId/{idDeck}
    @DeleteMapping("/deletarPorId/{idDeck}")
    public ResponseEntity<String> deletarDeckPorId(@PathVariable("idDeck") Integer idDeck) {
        Optional<Deck> deckOptional = deckRepository.findByIdDeck(idDeck);

        if (deckOptional.isPresent()) {
            deckRepository.deleteById(idDeck); // Deleta o deck pelo ID
            return ResponseEntity.ok("Deck deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck não encontrado");
        }
    }


    // http://localhost:8080/decks/deletarPorNome/{nome}
    @DeleteMapping("/deletarPorNome/{nome}")
    @Transactional
    public ResponseEntity<String> deletarDeckPorNome(@PathVariable("nome") String nome) {
        Optional<Deck> deckOptional = deckRepository.findByNome(nome);

        if (deckOptional.isPresent()) {
            deckRepository.deleteByNome(nome); // Deleta o deck pelo nome
            return ResponseEntity.ok("Deck deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck não encontrado");
        }
    }


}
