package com.inventariovista.repository;

import com.inventariovista.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {

    // 1. O painel principal: Traz todos os itens, MAS apenas os ativos
    List<ItemInventario> findByIsAtivoTrue();

    // 2. O filtro da tela: Traz itens ativos de uma UNIDADE específica (Estoril ou Savassi)
    List<ItemInventario> findByUnidadeIdAndIsAtivoTrue(Long unidadeId);

    // 3. O filtro de categoria: Traz itens ativos de uma categoria (ex: Cozinha)
    List<ItemInventario> findByCategoriaIdAndIsAtivoTrue(Long categoriaId);

    // 4. Segurança contra duplicidade: Busca item pelo Número de Patrimônio
    Optional<ItemInventario> findByCodigoPatrimonio(String codigoPatrimonio);

    // 5. Busca item pelo Número de Série (útil para o importador Python não duplicar itens)
    Optional<ItemInventario> findByNumeroSerie(String numeroSerie);
}