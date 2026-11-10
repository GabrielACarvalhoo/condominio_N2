
package gestao.persistence.dao;

import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UnidadeDAO {

    // Cadastrar uma unidade


    public void salvar(UnidadeEntity unidade) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                session.persist(unidade);

                transaction.commit();

                System.out.println("Unidade cadastrada com sucesso!");

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(
                        "Erro ao cadastrar unidade.", e
                );
            }
        }
    }



    // Buscar todas as unidades
    public List<UnidadeEntity> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session
                    .createQuery("FROM UnidadeEntity", UnidadeEntity.class)
                    .getResultList();

        }
    }

    // Buscar unidade pelo ID
    public UnidadeEntity buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.find(UnidadeEntity.class, id);

        }
    }

    // Atualizar uma unidade

    public void atualizar(UnidadeEntity unidade) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(unidade);

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception erroRollback) {
                    e.addSuppressed(erroRollback);
                }
            }

            throw new RuntimeException("Erro ao atualizar unidade.", e);
        }
    }

    // Excluir uma unidade

    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            UnidadeEntity unidade = session.find(UnidadeEntity.class, id);

            if (unidade == null) {
                System.out.println("Unidade não encontrada.");
                transaction.commit();
                return;
            }

            session.remove(unidade);

            transaction.commit();

            System.out.println("Unidade excluída com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception erroRollback) {
                    e.addSuppressed(erroRollback);
                }
            }

            throw new RuntimeException("Erro ao excluir unidade.", e);
        }
    }

}
