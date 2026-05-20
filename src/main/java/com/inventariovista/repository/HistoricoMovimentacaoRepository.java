package com.inventariovista.repository;

import com.inventariovista.model.HistoricoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoMovimentacaoRepository extends JpaRepository<HistoricoMovimentacao, Long> {
    List<HistoricoMovimentacao> findByItemIdOrderByDataMovimentacaoDesc(Long itemId);
}