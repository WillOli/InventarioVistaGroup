package com.inventariovista.repository;

import com.inventariovista.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    // Busca uma unidade pelo nome exato (útil na importação do Python)
    Optional<Unidade> findByNome(String nome);

    // Retorna apenas as unidades que não foram excluídas
    List<Unidade> findByIsAtivoTrue();
}