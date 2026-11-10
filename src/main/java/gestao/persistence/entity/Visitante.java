
package gestao.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "visitante")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visitante")
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 30)
    private String documento;

    @Column(length = 20)
    private String telefone;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public Visitante() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
