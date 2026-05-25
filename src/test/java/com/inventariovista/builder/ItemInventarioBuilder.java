package com.inventariovista.builder;

import com.inventariovista.model.ItemInventario;
import com.inventariovista.model.Unidade;

public class ItemInventarioBuilder {

    private ItemInventario item;

    // Construtor privado: cria uma instância com dados válidos por padrão (Caminho Feliz)
    private ItemInventarioBuilder() {
        item = new ItemInventario();
        item.setId(1L);
        item.setCodigoPatrimonio("PAT-PADRAO-001");
        item.setNome("Item de Teste Padrão");
        item.setAtivo(true);
        item.setEstado("Em uso");

        Unidade unidade = new Unidade();
        unidade.setId(1L);
        item.setUnidade(unidade);
    }

    // Método estático para iniciar o builder
    public static ItemInventarioBuilder umItem() {
        return new ItemInventarioBuilder();
    }

    // Métodos fluentes (Aqui está a magia do padrão Builder)
    public ItemInventarioBuilder comCodigoPatrimonio(String codigo) {
        item.setCodigoPatrimonio(codigo);
        return this; // Retorna o próprio builder para encadeamento
    }

    public ItemInventarioBuilder descartado() {
        item.setAtivo(false);
        item.setEstado("Descartado");
        return this;
    }

    public ItemInventarioBuilder naUnidade(Long unidadeId) {
        Unidade unidade = new Unidade();
        unidade.setId(unidadeId);
        item.setUnidade(unidade);
        return this;
    }

    // Método final que devolve o objeto construído
    public ItemInventario build() {
        return item;
    }
}