package com.meialuaquadrado.wild_cards.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meialuaquadrado.wild_cards.adapters.in.Deck;
import com.meialuaquadrado.wild_cards.adapters.in.DeckUsuario;
import com.meialuaquadrado.wild_cards.adapters.in.Usuario;
import com.meialuaquadrado.wild_cards.adapters.repositories.DeckRepository;
import com.meialuaquadrado.wild_cards.adapters.repositories.DeckUsuarioRepository;
import com.meialuaquadrado.wild_cards.adapters.repositories.UsuarioRepository;
import com.meialuaquadrado.wild_cards.model.DeckDTO;
import com.meialuaquadrado.wild_cards.model.DeckUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deckUsuarios")  // Criação de endpoint
public class DeckUsuarioController {

    @Autowired
    DeckUsuarioRepository deckUsuarioRepository;


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    ObjectMapper objectMapper;

//    // http://localhost:8080/decks/inserir
@PostMapping("/inserir")
public ResponseEntity<String> inserirDeckUsuario(@RequestBody DeckUsuarioDTO deckUsuarioDTO) {
    DeckUsuario deckUsuario = new DeckUsuario();

    Optional<Usuario> usuarioOpt = usuarioRepository.findByIdUsuario(deckUsuarioDTO.getIdUsuarioFk());
    Optional<Deck> deckOpt = deckRepository.findByIdDeck(deckUsuarioDTO.getIdDeckFk());

    deckUsuario.setUsuario(usuarioOpt.get());
    deckUsuario.setDeck(deckOpt.get());
//
//    // Verifica se um deck com o mesmo nome já existe
//    if (deckUsuarioRepository.findByNome(novoDeckDTO.getNome()).isPresent()) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um deck com esse nome.");
//    }

    deckUsuarioRepository.save(deckUsuario);
    return ResponseEntity.status(HttpStatus.CREATED).body("DeckUsuario criado com sucesso.");
}

    @GetMapping("/buscarTodos")
    public ResponseEntity<String> getDeckUsuario() {
        List<DeckUsuario> deckUsuarios = deckUsuarioRepository.findAll();

        if (!deckUsuarios.isEmpty()) {
            return ResponseEntity.ok().body(deckUsuarios.toString()); // Retorna a lista de DeckUsuarios
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum DeckUsuario encontrado");
        }
    }

    @GetMapping("/buscarPorId/{idDeckUsuario}")
    public ResponseEntity<String> getDeckUsuarioByIdDeckUsuario(@PathVariable("idDeckUsuario") Integer idDeckUsuario) throws JsonProcessingException {
        // Busca os DeckUsuario pelo ID do DeckUsuario
        DeckUsuario deckUsuarios = deckUsuarioRepository.findByIdDeckUsuario(idDeckUsuario).orElse(null);

        try {
            if (deckUsuarios == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeckUsuario não encontrado");
            } else {
                return ResponseEntity.ok(objectMapper.writeValueAsString(deckUsuarios));// Retorna a lista de deckUsuarios
            }

        } catch (JsonProcessingException e) {
            // Em caso de erro na serialização, retornar status 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar JSON");
        }
    }

    @GetMapping("/buscarPorUsuario/{idUsuario}")
    public ResponseEntity<String> getDeckUsuarioByUsuarioId(@PathVariable("idUsuario") Integer idUsuario) throws JsonProcessingException {
        // Busca os ingressos pelo ID do usuário
        List<DeckUsuario> deckUsuarios = deckUsuarioRepository.findByUsuarioId(idUsuario);

        if (deckUsuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeckUsuario não encontrado para esse usuário.");
        } else {
            // Retorna a lista de ingressos como JSON
            return ResponseEntity.ok().body(objectMapper.writeValueAsString(deckUsuarios));
        }
    }

    @GetMapping("/buscarPorDeck/{idDeck}")
    public ResponseEntity<String> getDeckUsuarioByDeckId(@PathVariable("idDeck") Integer idDeck) throws JsonProcessingException {
        // Busca os DeckUsuario pelo ID do Deck
        List<DeckUsuario> deckUsuarios = deckUsuarioRepository.findByDeckId(idDeck);

        if (deckUsuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum DeckUsuario encontrado para esse Deck.");
        } else {
            // Retorna a lista de DeckUsuarios como JSON
            return ResponseEntity.ok(objectMapper.writeValueAsString(deckUsuarios));
        }
    }


    @DeleteMapping ("/deletarPorId/{idDeckUsuario}")
    public ResponseEntity<String> deleteDeckUsuarioByIdDeckUsuario(@PathVariable("idDeckUsuario") Integer idDeckUsuario) {
        // Verifica se o DeckUsuario existe
        DeckUsuario deckUsuario = deckUsuarioRepository.findByIdDeckUsuario(idDeckUsuario).orElse(null);

        if (deckUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DeckUsuario não encontrado");
        }

        // Deleta o DeckUsuario
        deckUsuarioRepository.deleteById(deckUsuario.getIdDeckUsuario());
        return ResponseEntity.ok("DeckUsuario deletado com sucesso");
    }
}
