package com.inventariovista.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_unidades")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 255)
    private String endereco;

    @Column(name = "is_ativo")
    private Boolean isAtivo = true;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    // Garante que a data de criação seja preenchida automaticamente
    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    // --- GETTERS E SETTERS ---
    // Dica no IntelliJ: Aperte Alt + Insert -> Getter and Setter -> Selecione todos os campos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Boolean getAtivo() { return isAtivo; }
    public void setAtivo(Boolean ativo) { isAtivo = ativo; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}