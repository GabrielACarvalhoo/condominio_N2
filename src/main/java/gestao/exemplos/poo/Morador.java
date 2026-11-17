
package gestao.exemplos.poo;

public class Morador extends Pessoa {

    public Morador(String nome) {
        super(nome);
    }

    @Override
    public void apresentar() {
        System.out.println("Morador do condomínio: " + nome);
    }
}
