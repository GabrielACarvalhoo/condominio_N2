
package gestao.exemplos.poo;

public class DemonstracaoPOO {

    public static void main(String[] args) {

        System.out.println("=== DEMONSTRAÇÃO DE POO ===");

        // HERANÇA E SOBRESCRITA
        System.out.println("\n1. HERANÇA E SOBRESCRITA");

        Morador morador = new Morador("Carlos");
        morador.apresentar();

        // POLIMORFISMO
        System.out.println("\n2. POLIMORFISMO");

        Pessoa pessoa = new Morador("Gabriel");
        pessoa.apresentar();

        // SOBRECARGA
        System.out.println("\n3. SOBRECARGA");

        ConsultaPessoa consulta = new ConsultaPessoa();

        consulta.buscar(10);
        consulta.buscar("Maria");

        // INTERFACE
        System.out.println("\n4. INTERFACE");

        Notificavel notificacao = new NotificacaoEmail();
        notificacao.enviarNotificacao();

        System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===");
    }
}
