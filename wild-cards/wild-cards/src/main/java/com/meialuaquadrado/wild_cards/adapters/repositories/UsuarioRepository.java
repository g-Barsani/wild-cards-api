package com.meialuaquadrado.wild_cards.adapters.repositories;

import com.meialuaquadrado.wild_cards.adapters.in.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAll();

    Optional<Usuario> findByIdUsuario(Integer id);

}
