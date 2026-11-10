
package gestao.persistence.dao;

import gestao.persistence.entity.Visitante;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VisitanteDAO {

    // CADASTRAR VISITANTE
    public void salvar(Visitante visitante) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(visitante);

            transaction.commit();

            System.out.println("Visitante cadastrado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao cadastrar visitante: " + e.getMessage()
            );
        }
    }

    // LISTAR TODOS OS VISITANTES
    public List<Visitante> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "FROM Visitante v ORDER BY v.id DESC",
                    Visitante.class
            ).list();
        }
    }

    // BUSCAR VISITANTE POR ID
    public Visitante buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.get(Visitante.class, id);
        }
    }

    // ATUALIZAR VISITANTE
    public void atualizar(Visitante visitante) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(visitante);

            transaction.commit();

            System.out.println("Visitante atualizado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao atualizar visitante: " + e.getMessage()
            );
        }
    }

    // EXCLUIR VISITANTE
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            Visitante visitante = session.get(
                    Visitante.class,
                    id
            );

            if (visitante == null) {
                System.out.println("Visitante não encontrado!");
                transaction.rollback();
                return;
            }

            session.remove(visitante);

            transaction.commit();

            System.out.println("Visitante excluído com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao excluir visitante: " + e.getMessage()
            );
        }
    }
}
