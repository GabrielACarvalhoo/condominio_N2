
package gestao.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "taxa_condominial",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_taxa_unidade_competencia_desc",
                        columnNames = {"id_unidade", "competencia", "descricao"}
                )
        }
)
public class TaxaCondominial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taxa")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_unidade", nullable = false)
    private UnidadeEntity unidade;

    @Column(nullable = false, length = 150)
    private String descricao;

    @Column(
            name = "competencia",
            nullable = false,
            length = 7,
            columnDefinition = "CHAR(7)"
    )
    private String competencia;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate vencimento;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(nullable = false)
    private StatusTaxaCondominial status = StatusTaxaCondominial.ABERTA;

    public TaxaCondominial() {
    }

    public TaxaCondominial(
            UnidadeEntity unidade,
            String descricao,
            String competencia,
            BigDecimal valor,
            LocalDate vencimento
    ) {
        this.unidade = unidade;
        this.descricao = descricao;
        this.competencia = competencia;
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = StatusTaxaCondominial.ABERTA;
    }

    public Integer getId() {
        return id;
    }

    public UnidadeEntity getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeEntity unidade) {
        this.unidade = unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public StatusTaxaCondominial getStatus() {
        return status;
    }

    public void setStatus(StatusTaxaCondominial status) {
        this.status = status;
    }
}
