
package gestao.persistence.dao;

import gestao.persistence.entity.EspacoComum;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EspacoComumDAO {

    public void salvar(EspacoComum espaco) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(espaco);

            transaction.commit();

            System.out.println("Espaço cadastrado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao cadastrar espaço: " + e.getMessage(), e
            );
        }
    }

    public List<EspacoComum> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM EspacoComum ORDER BY nome",
                    EspacoComum.class
            ).list();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao listar espaços: " + e.getMessage(), e
            );
        }
    }
}
