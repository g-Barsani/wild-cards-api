package com.meialuaquadrado.wild_cards.adapters.out.controller;

//import MeiaLuaQuadrado.ventz.adapters.in.Usuario;
//import MeiaLuaQuadrado.ventz.adapters.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meialuaquadrado.wild_cards.adapters.in.Usuario;
import com.meialuaquadrado.wild_cards.adapters.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/usuarios")  // Criação de endpoint
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ObjectMapper objectMapper;

    // http://localhost:8080/usuarios/buscarTodos
    @GetMapping("/buscarTodos")
    public ResponseEntity<String> getUsuarios() {

            List<Usuario> usuarios = usuarioRepository.findAll();

            if (!usuarios.isEmpty()){
                return ResponseEntity.ok().body(usuarios.toString());
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum ingresso encontrado");
            }

    }

    /*
    http://localhost:8080/usuarios/inserirUsuario

    body:

    {
        "nome": "Sapo PRETO",
            "email": "sapo.preto@example.com",
            "cpf": "66624246969",
            "senha": "123"
    }

    */
    @PostMapping("/inserirUsuario")
    public ResponseEntity<String> inserirUsuario(@RequestBody Usuario usuario) {
        String email = usuario.getEmail();

        // Verifica no BD se algum usuário já tem o email informado
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(email);

        // Checa se o email já está cadastrado
        boolean emailCadastrado = usuarioEmail.isPresent();

        if (emailCadastrado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL JÁ CADASTRADO");
        } else {
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        }
    }

    // http://localhost:8080/usuarios/buscarPorId/3
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<String> getUsuarioById(@PathVariable("id") Integer idUsuario) {
        // Usar orElse(null) para retornar o valor ou null se não existir
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario).orElse(null);

        if (usuario != null) {
            return ResponseEntity.ok(usuario.toString());  // Retorna o objeto 'Usuario' como string com status 200
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");  // Retorna 404 com mensagem
        }
    }


    // http://localhost:8080/usuarios/buscarPorEmailESenha?email=sapo.preto@example.com&senha=123
    @GetMapping("/buscarPorEmailESenha")
    public ResponseEntity<String> validarUsuario(@RequestParam("email") String email, @RequestParam("senha") String senha) {
        // Busca o usuário pelo email e senha
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha).orElse(null);

        // Verifica se o usuário foi encontrado
        if (usuario != null) {
            return ResponseEntity.ok(usuario.toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciais inválidas");
        }
    }

    // http://localhost:8080/usuarios/buscarPorEmail?sapo.preto@example.com
    @GetMapping("/buscarPorEmail")
    public ResponseEntity<String> getUsuarioByEmail(@RequestParam("email") String email) {
        // Busca o usuário pelo email
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        // Verifica se o usuário foi encontrado
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get().toString());  // Retorna o objeto 'Usuario' como string com status 200
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");  // Retorna 404 com mensagem
        }
    }
}
