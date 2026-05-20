package com.inventariovista.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_historico_movimentacao")
public class HistoricoMovimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemInventario item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_origem_id")
    private Unidade unidadeOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_destino_id")
    private Unidade unidadeDestino;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao;

    @Column(name = "usuario_responsavel", length = 100)
    private String usuarioResponsavel;

    @PrePersist
    protected void onCreate() {
        this.dataMovimentacao = LocalDateTime.now();
    }

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ItemInventario getItem() { return item; }
    public void setItem(ItemInventario item) { this.item = item; }

    public Unidade getUnidadeOrigem() { return unidadeOrigem; }
    public void setUnidadeOrigem(Unidade unidadeOrigem) { this.unidadeOrigem = unidadeOrigem; }

    public Unidade getUnidadeDestino() { return unidadeDestino; }
    public void setUnidadeDestino(Unidade unidadeDestino) { this.unidadeDestino = unidadeDestino; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }

    public String getUsuarioResponsavel() { return usuarioResponsavel; }
    public void setUsuarioResponsavel(String usuarioResponsavel) { this.usuarioResponsavel = usuarioResponsavel; }
}