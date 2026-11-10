
package gestao.persistence.dao;

import gestao.persistence.entity.Ocorrencia;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OcorrenciaDAO {

    // CADASTRAR OCORRÊNCIA
    public void salvar(Ocorrencia ocorrencia) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(ocorrencia);

            transaction.commit();

            System.out.println("Ocorrência cadastrada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao cadastrar ocorrência: " + e.getMessage()
            );
        }
    }

    // LISTAR TODAS AS OCORRÊNCIAS
    public List<Ocorrencia> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "SELECT o FROM Ocorrencia o " +
                            "JOIN FETCH o.usuarioRegistro " +
                            "LEFT JOIN FETCH o.unidade " +
                            "ORDER BY o.id DESC",
                    Ocorrencia.class
            ).list();
        }
    }

    // BUSCAR OCORRÊNCIA POR ID
    public Ocorrencia buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                            "SELECT o FROM Ocorrencia o " +
                                    "JOIN FETCH o.usuarioRegistro " +
                                    "LEFT JOIN FETCH o.unidade " +
                                    "WHERE o.id = :id",
                            Ocorrencia.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    // ATUALIZAR OCORRÊNCIA
    public void atualizar(Ocorrencia ocorrencia) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(ocorrencia);

            transaction.commit();

            System.out.println("Ocorrência atualizada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao atualizar ocorrência: " + e.getMessage()
            );
        }
    }

    // EXCLUIR OCORRÊNCIA
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            Ocorrencia ocorrencia = session.get(
                    Ocorrencia.class,
                    id
            );

            if (ocorrencia == null) {
                System.out.println("Ocorrência não encontrada!");
                transaction.rollback();
                return;
            }

            session.remove(ocorrencia);

            transaction.commit();

            System.out.println("Ocorrência excluída com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao excluir ocorrência: " + e.getMessage()
            );
        }
    }
}
