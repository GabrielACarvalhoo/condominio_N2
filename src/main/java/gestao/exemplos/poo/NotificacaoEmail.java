
package gestao.exemplos.poo;

public class NotificacaoEmail implements Notificavel {

    @Override
    public void enviarNotificacao() {
        System.out.println("Notificação enviada por e-mail.");
    }
}
