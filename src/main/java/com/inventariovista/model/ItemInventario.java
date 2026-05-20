package com.inventariovista.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_itens_inventario")
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_patrimonio", unique = true, length = 50)
    private String codigoPatrimonio;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 100)
    private String modelo;

    @Column(name = "codigo_fabricante", length = 100)
    private String codigoFabricante;

    @Column(length = 50)
    private String tensao;

    @Column(length = 50)
    private String peso;

    @Column(length = 100)
    private String marca;

    @Column(name = "numero_serie", length = 150)
    private String numeroSerie;

    @Column(length = 100)
    private String localizacao;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "nota_fiscal_url", columnDefinition = "TEXT")
    private String notaFiscalUrl;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(length = 50)
    private String estado = "Em uso"; // Em uso, Guardado, Manutenção

    @Column(name = "foto_url", columnDefinition = "TEXT")
    private String fotoUrl;

    // --- RELACIONAMENTOS (CHAVES ESTRANGEIRAS) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // --- LÓGICA DE SOFT DELETE E AUDITORIA ---

    @Column(name = "is_ativo")
    private Boolean isAtivo = true;

    @Column(name = "data_desativacao")
    private LocalDateTime dataDesativacao;

    @Column(name = "motivo_desativacao", columnDefinition = "TEXT")
    private String motivoDesativacao;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    // --- MÉTODOS DE CICLO DE VIDA ---

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoPatrimonio() { return codigoPatrimonio; }
    public void setCodigoPatrimonio(String codigoPatrimonio) { this.codigoPatrimonio = codigoPatrimonio; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCodigoFabricante() { return codigoFabricante; }
    public void setCodigoFabricante(String codigoFabricante) { this.codigoFabricante = codigoFabricante; }

    public String getTensao() { return tensao; }
    public void setTensao(String tensao) { this.tensao = tensao; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getNotaFiscalUrl() { return notaFiscalUrl; }
    public void setNotaFiscalUrl(String notaFiscalUrl) { this.notaFiscalUrl = notaFiscalUrl; }

    public LocalDate getDataAquisicao() { return dataAquisicao; }
    public void setDataAquisicao(LocalDate dataAquisicao) { this.dataAquisicao = dataAquisicao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Boolean getAtivo() { return isAtivo; }
    public void setAtivo(Boolean ativo) { isAtivo = ativo; }

    public LocalDateTime getDataDesativacao() { return dataDesativacao; }
    public void setDataDesativacao(LocalDateTime dataDesativacao) { this.dataDesativacao = dataDesativacao; }

    public String getMotivoDesativacao() { return motivoDesativacao; }
    public void setMotivoDesativacao(String motivoDesativacao) { this.motivoDesativacao = motivoDesativacao; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm()
    { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm)
    { this.atualizadoEm = atualizadoEm; }
}