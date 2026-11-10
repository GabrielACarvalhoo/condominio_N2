
package gestao.persistence.teste.perfil;

import gestao.persistence.entity.Perfil;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class TestePerfil {

    public static void main(String[] args) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            List<Perfil> perfis = session
                    .createQuery("FROM Perfil", Perfil.class)
                    .getResultList();

            System.out.println("=== PERFIS CADASTRADOS ===");

            for (Perfil perfil : perfis) {
                System.out.println(
                        perfil.getId() + " - " +
                                perfil.getNome() + " - " +
                                perfil.getDescricao()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.fechar();
        }
    }
}
