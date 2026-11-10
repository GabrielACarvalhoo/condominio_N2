
package gestao.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "unidade",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_unidade_bloco_numero",
                        columnNames = {"bloco", "numero"}
                )
        }
)
public class UnidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidade")
    private Integer id;

    @Column(length = 30)
    private String bloco;

    @Column(nullable = false, length = 20)
    private String numero;

    private Integer andar;

    @Column(precision = 8, scale = 2)
    private BigDecimal metragem;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public UnidadeEntity() {
    }

    public UnidadeEntity(
            String bloco,
            String numero,
            Integer andar,
            BigDecimal metragem,
            String observacoes
    ) {
        this.bloco = bloco;
        this.numero = numero;
        this.andar = andar;
        this.metragem = metragem;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public BigDecimal getMetragem() {
        return metragem;
    }

    public void setMetragem(BigDecimal metragem) {
        this.metragem = metragem;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
