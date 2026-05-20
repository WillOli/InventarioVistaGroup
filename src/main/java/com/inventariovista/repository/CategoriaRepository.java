package com.inventariovista.repository;

import com.inventariovista.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca categoria pelo nome exato
    Optional<Categoria> findByNome(String nome);

    // Retorna apenas categorias ativas
    List<Categoria> findByIsAtivoTrue();
}