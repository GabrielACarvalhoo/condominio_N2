
package gestao.persistence.dao;

import gestao.persistence.entity.Funcionario;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FuncionarioDAO {

    // CADASTRAR FUNCIONÁRIO
    public void salvar(Funcionario funcionario) {

        validarFuncionario(funcionario);

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(funcionario);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao cadastrar funcionário.", e
            );
        }
    }

    // LISTAR TODOS OS FUNCIONÁRIOS
    public List<Funcionario> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "SELECT f FROM Funcionario f " +
                            "JOIN FETCH f.usuario " +
                            "JOIN FETCH f.cargo " +
                            "ORDER BY f.id",
                    Funcionario.class
            ).list();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao listar funcionários.", e
            );
        }
    }

    // BUSCAR FUNCIONÁRIO POR ID
    public Funcionario buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                            "SELECT f FROM Funcionario f " +
                                    "JOIN FETCH f.usuario " +
                                    "JOIN FETCH f.cargo " +
                                    "WHERE f.id = :id",
                            Funcionario.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao buscar funcionário.", e
            );
        }
    }

    // ATUALIZAR FUNCIONÁRIO
    public void atualizar(Funcionario funcionario) {

        validarFuncionario(funcionario);

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(funcionario);

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao atualizar funcionário.", e
            );
        }
    }

    // EXCLUIR FUNCIONÁRIO
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            Funcionario funcionario = session.find(
                    Funcionario.class, id
            );

            if (funcionario == null) {
                transaction.rollback();
                return;
            }

            session.remove(funcionario);

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao excluir funcionário.", e
            );
        }
    }

    // VALIDAR DADOS DO FUNCIONÁRIO
    private void validarFuncionario(Funcionario funcionario) {

        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "O funcionário não pode ser nulo."
            );
        }

        if (funcionario.getUsuario() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar um usuário."
            );
        }

        if (funcionario.getCargo() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar um cargo."
            );
        }

        if (funcionario.getDataAdmissao() != null
                && funcionario.getDataDemissao() != null
                && funcionario.getDataDemissao()
                .isBefore(funcionario.getDataAdmissao())) {

            throw new IllegalArgumentException(
                    "A data de demissão não pode ser anterior à admissão."
            );
        }
    }
}
