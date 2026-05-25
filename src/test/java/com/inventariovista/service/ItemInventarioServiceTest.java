package com.inventariovista.service;

import com.inventariovista.builder.ItemInventarioBuilder;
import com.inventariovista.model.ItemInventario;
import com.inventariovista.repository.HistoricoMovimentacaoRepository;
import com.inventariovista.repository.ItemInventarioRepository;
import com.inventariovista.repository.UnidadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemInventarioServiceTest {

    @Mock // "Finge" ser o banco de dados
    private ItemInventarioRepository itemRepository;

    @Mock
    private UnidadeRepository unidadeRepository;

    @Mock
    private HistoricoMovimentacaoRepository historicoRepository;

    @InjectMocks // Injeta os mocks falsos dentro do nosso serviço real
    private ItemInventarioService itemService;

    @Test
    @DisplayName("Deve salvar um item com sucesso quando o código de patrimônio não existir")
    void deveSalvarItemComSucesso() {
        // Arrange (Preparar) - Usando o nosso Builder!
        ItemInventario novoItem = ItemInventarioBuilder.umItem().comCodigoPatrimonio("NOVO-001").build();

        when(itemRepository.findByCodigoPatrimonio("NOVO-001")).thenReturn(Optional.empty());
        when(itemRepository.save(any(ItemInventario.class))).thenReturn(novoItem);

        // Act (Agir)
        ItemInventario itemSalvo = itemService.salvarItem(novoItem);

        // Assert (Verificar)
        assertNotNull(itemSalvo);
        assertEquals("NOVO-001", itemSalvo.getCodigoPatrimonio());
        verify(itemRepository, times(1)).save(novoItem); // Verifica se o save foi chamado 1x
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar item com patrimônio duplicado")
    void deveLancarExcecaoQuandoPatrimonioDuplicado() {
        // Arrange (Preparar)
        ItemInventario itemExistente = ItemInventarioBuilder.umItem().comCodigoPatrimonio("DUPLICADO-123").build();

        // Simulamos que o banco encontrou um item com este código
        when(itemRepository.findByCodigoPatrimonio("DUPLICADO-123")).thenReturn(Optional.of(itemExistente));

        // Act & Assert (Agir e Verificar a Exceção)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemService.salvarItem(itemExistente);
        });

        assertEquals("Erro: O Código de Patrimônio DUPLICADO-123 já está em uso.", exception.getMessage());
        verify(itemRepository, never()).save(any()); // Garante que o item NUNCA foi salvo
    }

    @Test
    @DisplayName("Não deve permitir transferir um item que já está descartado")
    void naoDeveTransferirItemDescartado() {
        // Arrange (Preparar) - Criamos um item já descartado usando o Builder!
        ItemInventario itemLixo = ItemInventarioBuilder.umItem().descartado().build();

        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemLixo));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemService.transferirItem(1L, 2L, "Tentando transferir sucata");
        });

        assertEquals("Erro: Não é possível transferir um item que já foi descartado.", exception.getMessage());
    }
}