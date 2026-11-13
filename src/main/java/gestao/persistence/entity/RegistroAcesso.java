
package gestao.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_acesso")
public class RegistroAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acesso")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autorizacao", nullable = false)
    private AutorizacaoVisitante autorizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario_portaria")
    private Funcionario funcionarioPortaria;

    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Column(name = "saida")
    private LocalDateTime saida;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    public RegistroAcesso() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AutorizacaoVisitante getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(AutorizacaoVisitante autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Funcionario getFuncionarioPortaria() {
        return funcionarioPortaria;
    }

    public void setFuncionarioPortaria(Funcionario funcionarioPortaria) {
        this.funcionarioPortaria = funcionarioPortaria;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
