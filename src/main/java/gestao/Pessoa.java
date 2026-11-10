
package gestao;

public abstract class Pessoa {

    protected String nome;

    protected Pessoa() {
    }

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract void exibirIdentificacao();
}
