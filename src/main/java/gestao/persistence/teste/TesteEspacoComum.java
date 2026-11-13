
package gestao.persistence.teste;

import gestao.persistence.dao.EspacoComumDAO;
import gestao.persistence.entity.EspacoComum;
import gestao.persistence.util.HibernateUtil;

public class TesteEspacoComum {

    public static void main(String[] args) {

        EspacoComumDAO dao = new EspacoComumDAO();

        try {

            EspacoComum espaco = new EspacoComum();

            espaco.setNome("Churrasqueira");
            espaco.setDescricao("Área com churrasqueira para moradores");
            espaco.setCapacidade(20);
            espaco.setRegras("Reservar com antecedência");
            espaco.setAtivo(true);

            dao.salvar(espaco);

            System.out.println("\n--- ESPAÇOS CADASTRADOS ---");

            for (EspacoComum item : dao.listarTodos()) {

                System.out.println(
                        item.getId() + " - " +
                                item.getNome() + " - " +
                                item.getCapacidade() + " pessoas"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            HibernateUtil.fechar();
        }
    }
}
