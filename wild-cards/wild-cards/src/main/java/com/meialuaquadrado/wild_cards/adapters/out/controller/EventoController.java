//package adapters.out.controller;
//
//import MeiaLuaQuadrado.ventz.adapters.in.Evento;
//import MeiaLuaQuadrado.ventz.adapters.repositories.EventoRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/eventos")  // Criação de endpoint
//public class EventoController {
//
//    @Autowired
//    EventoRepository eventoRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @GetMapping("/buscarTodos")
//    public ResponseEntity<String> getEvento()  throws JsonProcessingException {
//        List<Evento> eventos = eventoRepository.findAll();
//        if (!eventos.isEmpty()) {
//            return ResponseEntity.ok().body(eventos.toString()); // Retorna a lista de ingressos
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado");
//        }
//    }
//
//    //buscar evento por id
//    // http://localhost:8080/eventos/buscarPorUsuario/1
//    @GetMapping("/buscarPorUsuario/{idUsuario}")
//    public ResponseEntity<String> getEventoByUsuarioId(@PathVariable("idUsuario") Integer idUsuario) throws JsonProcessingException {
//        // Busca os ingressos pelo ID do usuário
//        List<Evento> eventos = eventoRepository.findByUsuarioId(idUsuario);
//
//        if (eventos.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingressos não encontrados para esse usuário.");
//        } else {
//            // Retorna a lista de ingressos como JSON
//            return ResponseEntity.ok().body(objectMapper.writeValueAsString(eventos));
//        }
//    }
//
//    //buscar evento por usuario
//    //    http://localhost:8080/eventos/buscarPorId/1
//    @GetMapping("/buscarPorId/{idEvento}")
//    public ResponseEntity<String> getEventoByIdEvento(@PathVariable("idEvento") Integer idEvento) throws JsonProcessingException {
//        // Busca os ingressos pelo ID do usuário
//        Evento eventos = eventoRepository.findByIdEvento(idEvento).orElse(null);
//
//        try {
//
//            if (eventos == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado");
//            } else {
//                return ResponseEntity.ok(objectMapper.writeValueAsString(eventos));// Retorna a lista de ingressos
//            }
//        }catch (JsonProcessingException e) {
//            // Em caso de erro na serialização, retornar status 500
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar JSON");
//        }
//    }
//
//    @PostMapping("/inserirEvento")
//    public ResponseEntity<String> inserirEvento(@RequestBody Evento evento) {
//        try {
//            // Salva o ingresso no repositório
//            eventoRepository.save(evento);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Evento cadastrado com sucesso!");
//        } catch (Exception e) {
//            // Se ocorrer algum erro, retorna um status 500 (erro interno do servidor)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar evento: " + e.getMessage());
//        }
//    }
//}
