
package gestao.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "autorizacao_visitante")
public class AutorizacaoVisitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autorizacao")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante", nullable = false)
    private Visitante visitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidade", nullable = false)
    private UnidadeEntity unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_autorizador", nullable = false)
    private Usuario usuarioAutorizador;

    @Column(name = "inicio_validade", nullable = false)
    private LocalDateTime inicioValidade;

    @Column(name = "fim_validade")
    private LocalDateTime fimValidade;

    @Column(name = "quantidade_acessos")
    private Integer quantidadeAcessos;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(
            nullable = false,
            columnDefinition = "ENUM('PENDENTE','APROVADA','CANCELADA','EXPIRADA')"
    )
    private StatusAutorizacao status = StatusAutorizacao.PENDENTE;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public AutorizacaoVisitante() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public UnidadeEntity getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeEntity unidade) {
        this.unidade = unidade;
    }

    public Usuario getUsuarioAutorizador() {
        return usuarioAutorizador;
    }

    public void setUsuarioAutorizador(Usuario usuarioAutorizador) {
        this.usuarioAutorizador = usuarioAutorizador;
    }

    public LocalDateTime getInicioValidade() {
        return inicioValidade;
    }

    public void setInicioValidade(LocalDateTime inicioValidade) {
        this.inicioValidade = inicioValidade;
    }

    public LocalDateTime getFimValidade() {
        return fimValidade;
    }

    public void setFimValidade(LocalDateTime fimValidade) {
        this.fimValidade = fimValidade;
    }

    public Integer getQuantidadeAcessos() {
        return quantidadeAcessos;
    }

    public void setQuantidadeAcessos(Integer quantidadeAcessos) {
        this.quantidadeAcessos = quantidadeAcessos;
    }

    public StatusAutorizacao getStatus() {
        return status;
    }

    public void setStatus(StatusAutorizacao status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
