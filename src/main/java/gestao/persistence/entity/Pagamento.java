
package gestao.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_taxa", nullable = false)
    private TaxaCondominial taxa;

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "pago_em", nullable = false)
    private LocalDateTime pagoEm;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(length = 500)
    private String comprovante;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public Pagamento() {
    }

    public Pagamento(
            TaxaCondominial taxa,
            BigDecimal valorPago,
            LocalDateTime pagoEm,
            FormaPagamento formaPagamento,
            String comprovante,
            String observacoes
    ) {
        this.taxa = taxa;
        this.valorPago = valorPago;
        this.pagoEm = pagoEm;
        this.formaPagamento = formaPagamento;
        this.comprovante = comprovante;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public TaxaCondominial getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaCondominial taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDateTime getPagoEm() {
        return pagoEm;
    }

    public void setPagoEm(LocalDateTime pagoEm) {
        this.pagoEm = pagoEm;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
