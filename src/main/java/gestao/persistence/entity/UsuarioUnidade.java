
package gestao.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_unidade")
public class UsuarioUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_unidade")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidade", nullable = false)
    private UnidadeEntity unidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vinculo", nullable = false)
    private TipoVinculo tipoVinculo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "responsavel_financeiro", nullable = false)
    private Boolean responsavelFinanceiro = false;

    public UsuarioUnidade() {
    }
    public UsuarioUnidade(
            Usuario usuario,
            UnidadeEntity unidade,
            TipoVinculo tipoVinculo,
            LocalDate dataInicio,
            Boolean responsavelFinanceiro
    ) {
        this.usuario = usuario;
        this.unidade = unidade;
        this.tipoVinculo = tipoVinculo;
        this.dataInicio = dataInicio;
        this.responsavelFinanceiro = responsavelFinanceiro;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UnidadeEntity getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeEntity unidade) {
        this.unidade = unidade;
    }

    public TipoVinculo getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(TipoVinculo tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Boolean getResponsavelFinanceiro() {
        return responsavelFinanceiro;
    }

    public void setResponsavelFinanceiro(Boolean responsavelFinanceiro) {
        this.responsavelFinanceiro = responsavelFinanceiro;
    }
}
