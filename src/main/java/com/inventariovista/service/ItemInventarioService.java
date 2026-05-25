package com.inventariovista.service;

import com.inventariovista.model.HistoricoMovimentacao;
import com.inventariovista.model.ItemInventario;
import com.inventariovista.model.Unidade;
import com.inventariovista.repository.HistoricoMovimentacaoRepository;
import com.inventariovista.repository.ItemInventarioRepository;
import com.inventariovista.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemInventarioService {

    // Injeção de dependência dos repositórios via Construtor
    private final ItemInventarioRepository itemRepository;
    private final UnidadeRepository unidadeRepository;
    private final HistoricoMovimentacaoRepository historicoRepository; // NOVO: Repositório de Histórico

    public ItemInventarioService(ItemInventarioRepository itemRepository,
                                 UnidadeRepository unidadeRepository,
                                 HistoricoMovimentacaoRepository historicoRepository) {
        this.itemRepository = itemRepository;
        this.unidadeRepository = unidadeRepository;
        this.historicoRepository = historicoRepository; // NOVO: Inicializando no construtor
    }

    // -------------------------------------------------------------------
    // 1. BUSCAS BÁSICAS
    // -------------------------------------------------------------------

    public List<ItemInventario> listarTodosAtivos() {
        return itemRepository.findByIsAtivoTrue();
    }

    public List<ItemInventario> listarAtivosPorUnidade(Long unidadeId) {
        return itemRepository.findByUnidadeIdAndIsAtivoTrue(unidadeId);
    }

    // -------------------------------------------------------------------
    // 2. LÓGICA DE DESCARTE (SOFT DELETE)
    // -------------------------------------------------------------------

    @Transactional
    public ItemInventario descartarItem(Long itemId, String motivo) {
        // Busca o item ou lança erro se não achar
        ItemInventario item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Erro: Item com ID " + itemId + " não encontrado."));

        // Aplica as regras de negócio do descarte
        item.setAtivo(false);
        item.setEstado("Descartado");
        item.setMotivoDesativacao(motivo);
        item.setDataDesativacao(LocalDateTime.now());

        // Salva e retorna o item atualizado
        return itemRepository.save(item);
    }

    // -------------------------------------------------------------------
    // 3. LÓGICA DE TRANSFERÊNCIA ENTRE UNIDADES
    // -------------------------------------------------------------------

    @Transactional
    public ItemInventario transferirItem(Long itemId, Long novaUnidadeId, String motivoTransferencia) {
        // 1. Busca o item
        ItemInventario item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Erro: Item não encontrado."));

        // 2. Verifica se o item não está descartado
        if (!item.getAtivo()) {
            throw new RuntimeException("Erro: Não é possível transferir um item que já foi descartado.");
        }

        // 3. Busca a nova unidade (Ex: Savassi)
        Unidade novaUnidade = unidadeRepository.findById(novaUnidadeId)
                .orElseThrow(() -> new RuntimeException("Erro: Unidade de destino não encontrada."));

        // Guarda a unidade antiga para o histórico (log)
        Unidade unidadeOrigem = item.getUnidade();

        // 4. Executa a transferência
        item.setUnidade(novaUnidade);
        item.setEstado("Em uso"); // Volta para uso caso estivesse guardado

        ItemInventario itemSalvo = itemRepository.save(item);

        // 5. Salva o Log de Movimentação automatizado (Substituindo o antigo TODO)
        HistoricoMovimentacao historico = new HistoricoMovimentacao();
        historico.setItem(itemSalvo);
        historico.setUnidadeOrigem(unidadeOrigem);
        historico.setUnidadeDestino(novaUnidade);
        historico.setMotivo(motivoTransferencia);
        historico.setUsuarioResponsavel("William"); // Nome temporário até colocarmos login no sistema
        historicoRepository.save(historico);

        return itemSalvo;
    }

    // -------------------------------------------------------------------
    // 4. CADASTRO DE NOVO ITEM
    // -------------------------------------------------------------------

    @Transactional
    public ItemInventario salvarItem(ItemInventario novoItem) {
        // Regra: Verifica se o N° de Patrimônio já existe para evitar duplicidade
        if (novoItem.getCodigoPatrimonio() != null && !novoItem.getCodigoPatrimonio().isEmpty()) {
            itemRepository.findByCodigoPatrimonio(novoItem.getCodigoPatrimonio())
                    .ifPresent(i -> {
                        throw new RuntimeException("Erro: O Código de Patrimônio " + novoItem.getCodigoPatrimonio() + " já está em uso.");
                    });
        }
        return itemRepository.save(novoItem);
    }

    public List<ItemInventario> listarTodosInativos() {
        return itemRepository.findByIsAtivoFalse();
    }
}