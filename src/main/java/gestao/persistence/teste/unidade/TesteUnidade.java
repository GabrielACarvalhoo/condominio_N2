
package gestao.persistence.teste.unidade;

import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class TesteUnidade {

    public static void main(String[] args) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            List<UnidadeEntity> unidades = session
                    .createQuery("FROM UnidadeEntity", UnidadeEntity.class)
                    .getResultList();

            System.out.println("=== UNIDADES CADASTRADAS ===");

            for (UnidadeEntity unidade : unidades) {
                System.out.println(
                        "ID: " + unidade.getId() +
                                " | Bloco: " + unidade.getBloco() +
                                " | Número: " + unidade.getNumero() +
                                " | Andar: " + unidade.getAndar() +
                                " | Metragem: " + unidade.getMetragem()
                );
            }

            System.out.println("Total de unidades: " + unidades.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.fechar();
        }
    }
}
