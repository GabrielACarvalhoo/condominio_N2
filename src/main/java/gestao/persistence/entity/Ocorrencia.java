
package gestao.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencia")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ocorrencia")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidade")
    private UnidadeEntity unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_registro", nullable = false)
    private Usuario usuarioRegistro;

    @Column(name = "id_funcionario_responsavel")
    private Integer idFuncionarioResponsavel;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(
            nullable = false,
            columnDefinition = "ENUM('PORTARIA','SEGURANCA','CONVIVENCIA','INFRAESTRUTURA','OUTRA')"
    )
    private CategoriaOcorrencia categoria;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(
            name = "registrada_em",
            insertable = false,
            updatable = false
    )
    private LocalDateTime registradaEm;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(
            nullable = false,
            columnDefinition = "ENUM('ABERTA','EM_ANALISE','RESOLVIDA','CANCELADA')"
    )
    private StatusOcorrencia status = StatusOcorrencia.ABERTA;

    public Ocorrencia() {
    }

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

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Integer getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(Integer idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public CategoriaOcorrencia getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaOcorrencia categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getRegistradaEm() {
        return registradaEm;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }
}
