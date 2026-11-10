
package gestao.persistence.dao;

import gestao.persistence.entity.AutorizacaoVisitante;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class AutorizacaoVisitanteDAO {

    // CADASTRAR AUTORIZAÇÃO
    public void salvar(AutorizacaoVisitante autorizacao) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(autorizacao);

            transaction.commit();

            System.out.println("Autorização cadastrada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao cadastrar autorização: " + e.getMessage()
            );
        }
    }

    // LISTAR TODAS AS AUTORIZAÇÕES
    public List<AutorizacaoVisitante> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "SELECT a FROM AutorizacaoVisitante a " +
                            "JOIN FETCH a.visitante " +
                            "JOIN FETCH a.unidade " +
                            "JOIN FETCH a.usuarioAutorizador " +
                            "ORDER BY a.id DESC",
                    AutorizacaoVisitante.class
            ).list();
        }
    }

    // BUSCAR AUTORIZAÇÃO POR ID
    public AutorizacaoVisitante buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                            "SELECT a FROM AutorizacaoVisitante a " +
                                    "JOIN FETCH a.visitante " +
                                    "JOIN FETCH a.unidade " +
                                    "JOIN FETCH a.usuarioAutorizador " +
                                    "WHERE a.id = :id",
                            AutorizacaoVisitante.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    // ATUALIZAR AUTORIZAÇÃO
    public void atualizar(AutorizacaoVisitante autorizacao) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(autorizacao);

            transaction.commit();

            System.out.println("Autorização atualizada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao atualizar autorização: " + e.getMessage()
            );
        }
    }

    // EXCLUIR AUTORIZAÇÃO
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            AutorizacaoVisitante autorizacao = session.get(
                    AutorizacaoVisitante.class,
                    id
            );

            if (autorizacao == null) {
                System.out.println("Autorização não encontrada!");
                transaction.rollback();
                return;
            }

            session.remove(autorizacao);

            transaction.commit();

            System.out.println("Autorização excluída com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println(
                    "Erro ao excluir autorização: " + e.getMessage()
            );
        }
    }
}
