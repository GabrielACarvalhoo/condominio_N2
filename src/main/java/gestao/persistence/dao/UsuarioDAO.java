
package gestao.persistence.dao;

import gestao.persistence.entity.Usuario;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDAO {

    // Cadastrar usuário
    public void salvar(Usuario usuario) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                session.persist(usuario);
                transaction.commit();

                System.out.println("Usuário cadastrado com sucesso!");

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException("Erro ao cadastrar usuário.", e);
            }
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT u FROM Usuario u JOIN FETCH u.perfil",
                    Usuario.class
            ).list();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários.", e);
        }
    }

    // Buscar usuário pelo ID
    public Usuario buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.find(Usuario.class, id);
        }
    }

    // Atualizar usuário
    public void atualizar(Usuario usuario) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                session.merge(usuario);
                transaction.commit();

                System.out.println("Usuário atualizado com sucesso!");

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException("Erro ao atualizar usuário.", e);
            }
        }
    }

    // Excluir usuário
    public void excluir(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Usuario usuario = session.find(Usuario.class, id);

                if (usuario == null) {
                    System.out.println("Usuário não encontrado.");
                    transaction.commit();
                    return;
                }

                session.remove(usuario);
                transaction.commit();

                System.out.println("Usuário excluído com sucesso!");

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException("Erro ao excluir usuário.", e);
            }
        }
    }
}
