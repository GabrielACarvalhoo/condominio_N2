
package gestao.persistence.dao;

import gestao.persistence.entity.Cargo;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CargoDAO {

    // CADASTRAR CARGO
    public void salvar(Cargo cargo) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(cargo);

            transaction.commit();

            System.out.println("Cargo cadastrado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Erro ao cadastrar cargo.", e);
        }
    }

    // LISTAR TODOS OS CARGOS
    public List<Cargo> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "FROM Cargo c ORDER BY c.nome",
                    Cargo.class
            ).list();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao listar cargos.", e);
        }
    }

    // BUSCAR CARGO POR ID
    public Cargo buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.find(Cargo.class, id);

        } catch (Exception e) {

            throw new RuntimeException("Erro ao buscar cargo.", e);
        }
    }

    // ATUALIZAR CARGO
    public void atualizar(Cargo cargo) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(cargo);

            transaction.commit();

            System.out.println("Cargo atualizado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Erro ao atualizar cargo.", e);
        }
    }

    // EXCLUIR CARGO
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            Cargo cargo = session.find(Cargo.class, id);

            if (cargo == null) {
                transaction.rollback();
                System.out.println("Cargo não encontrado!");
                return;
            }

            session.remove(cargo);

            transaction.commit();

            System.out.println("Cargo excluído com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao excluir cargo. Verifique se existem funcionários vinculados a ele.",
                    e
            );
        }
    }
}
