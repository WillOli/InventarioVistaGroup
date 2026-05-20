package com.inventariovista.controller;

import com.inventariovista.model.ItemInventario;
import com.inventariovista.service.ItemInventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*") // Permite que um frontend em porta diferente faça requisições
public class ItemInventarioController {

    private final ItemInventarioService itemService;

    // Injeção do Service
    public ItemInventarioController(ItemInventarioService itemService) {
        this.itemService = itemService;
    }

    // 1. Listar todos os itens ativos
    @GetMapping
    public ResponseEntity<List<ItemInventario>> listarTodos() {
        List<ItemInventario> itens = itemService.listarTodosAtivos();
        return ResponseEntity.ok(itens);
    }

    // 2. Listar itens ativos por unidade
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ItemInventario>> listarPorUnidade(@PathVariable Long unidadeId) {
        List<ItemInventario> itens = itemService.listarAtivosPorUnidade(unidadeId);
        return ResponseEntity.ok(itens);
    }

    // 3. Cadastrar um novo item
    @PostMapping
    public ResponseEntity<ItemInventario> criar(@RequestBody ItemInventario item) {
        ItemInventario novoItem = itemService.salvarItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    // 4. Descartar um item (Soft Delete)
    @PatchMapping("/{id}/descartar")
    public ResponseEntity<ItemInventario> descartar(
            @PathVariable Long id,
            @RequestParam String motivo) {
        ItemInventario itemDescartado = itemService.descartarItem(id, motivo);
        return ResponseEntity.ok(itemDescartado);
    }

    // 5. Transferir um item entre unidades
    @PostMapping("/{id}/transferir")
    public ResponseEntity<ItemInventario> transferir(
            @PathVariable Long id,
            @RequestParam Long novaUnidadeId,
            @RequestParam String motivo) {
        ItemInventario itemTransferido = itemService.transferirItem(id, novaUnidadeId, motivo);
        return ResponseEntity.ok(itemTransferido);
    }
}