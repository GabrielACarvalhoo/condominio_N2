
package gestao.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "encomenda")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encomenda")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidade", nullable = false)
    private UnidadeEntity unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario_recebimento")
    private Funcionario funcionarioRecebimento;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(length = 100)
    private String transportadora;

    @Column(name = "codigo_rastreio", length = 100)
    private String codigoRastreio;

    @Column(name = "recebida_em", insertable = false, updatable = false)
    private LocalDateTime recebidaEm;

    @Column(name = "retirada_em")
    private LocalDateTime retiradaEm;

    @Column(name = "retirado_por", length = 120)
    private String retiradoPor;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(nullable = false, columnDefinition = "ENUM('RECEBIDA','RETIRADA','DEVOLVIDA')")
    private StatusEncomenda status = StatusEncomenda.RECEBIDA;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public Encomenda() {
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnidadeEntity getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeEntity unidade) {
        this.unidade = unidade;
    }

    public Funcionario getFuncionarioRecebimento() {
        return funcionarioRecebimento;
    }

    public void setFuncionarioRecebimento(Funcionario funcionarioRecebimento) {
        this.funcionarioRecebimento = funcionarioRecebimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public LocalDateTime getRecebidaEm() {
        return recebidaEm;
    }

    public void setRecebidaEm(LocalDateTime recebidaEm) {
        this.recebidaEm = recebidaEm;
    }

    public LocalDateTime getRetiradaEm() {
        return retiradaEm;
    }

    public void setRetiradaEm(LocalDateTime retiradaEm) {
        this.retiradaEm = retiradaEm;
    }

    public String getRetiradoPor() {
        return retiradoPor;
    }

    public void setRetiradoPor(String retiradoPor) {
        this.retiradoPor = retiradoPor;
    }

    public StatusEncomenda getStatus() {
        return status;
    }

    public void setStatus(StatusEncomenda status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
