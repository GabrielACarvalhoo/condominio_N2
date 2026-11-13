
package gestao.persistence.dao;

import gestao.persistence.entity.RegistroAcesso;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegistroAcessoDAO {

    // CADASTRAR REGISTRO DE ACESSO
    public void salvar(RegistroAcesso registro) {

        validarRegistro(registro);

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.persist(registro);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao cadastrar registro de acesso.", e
            );
        }
    }

    // LISTAR TODOS OS REGISTROS
    public List<RegistroAcesso> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                    "SELECT r FROM RegistroAcesso r " +
                            "JOIN FETCH r.autorizacao a " +
                            "JOIN FETCH a.visitante " +
                            "JOIN FETCH a.unidade " +
                            "LEFT JOIN FETCH r.funcionarioPortaria f " +
                            "LEFT JOIN FETCH f.usuario " +
                            "ORDER BY r.entrada DESC",
                    RegistroAcesso.class
            ).list();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao listar registros de acesso.", e
            );
        }
    }

    // BUSCAR REGISTRO POR ID
    public RegistroAcesso buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                            "SELECT r FROM RegistroAcesso r " +
                                    "JOIN FETCH r.autorizacao a " +
                                    "JOIN FETCH a.visitante " +
                                    "JOIN FETCH a.unidade " +
                                    "LEFT JOIN FETCH r.funcionarioPortaria f " +
                                    "LEFT JOIN FETCH f.usuario " +
                                    "WHERE r.id = :id",
                            RegistroAcesso.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao buscar registro de acesso.", e
            );
        }
    }

    // ATUALIZAR REGISTRO
    public void atualizar(RegistroAcesso registro) {

        validarRegistro(registro);

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            session.merge(registro);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao atualizar registro de acesso.", e
            );
        }
    }

    // EXCLUIR REGISTRO
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            RegistroAcesso registro = session.find(
                    RegistroAcesso.class, id
            );

            if (registro == null) {
                transaction.rollback();
                return;
            }

            session.remove(registro);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao excluir registro de acesso.", e
            );
        }
    }

    // VALIDAR REGISTRO
    private void validarRegistro(RegistroAcesso registro) {

        if (registro == null) {
            throw new IllegalArgumentException(
                    "O registro de acesso não pode ser nulo."
            );
        }

        if (registro.getAutorizacao() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar uma autorização."
            );
        }

        if (registro.getEntrada() == null) {
            throw new IllegalArgumentException(
                    "A data e hora de entrada são obrigatórias."
            );
        }

        if (registro.getSaida() != null &&
                registro.getSaida().isBefore(registro.getEntrada())) {

            throw new IllegalArgumentException(
                    "A saída não pode ser anterior à entrada."
            );
        }
    }
}
