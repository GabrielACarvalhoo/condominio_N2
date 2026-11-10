
package gestao.persistence.dao;

import gestao.persistence.entity.UsuarioUnidade;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioUnidadeDAO {

    // Cadastrar vínculo

    // Cadastrar vínculo
    public void salvar(UsuarioUnidade vinculo) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            // Verificar se o usuário já está vinculado à unidade
            Long quantidade = session.createQuery(
                            "SELECT COUNT(uu) FROM UsuarioUnidade uu " +
                                    "WHERE uu.usuario.id = :idUsuario " +
                                    "AND uu.unidade.id = :idUnidade",
                            Long.class
                    )
                    .setParameter("idUsuario", vinculo.getUsuario().getId())
                    .setParameter("idUnidade", vinculo.getUnidade().getId())
                    .uniqueResult();

            // Impedir o cadastro de um vínculo duplicado
            if (quantidade != null && quantidade > 0) {

                System.out.println(
                        "Este usuário já possui vínculo com esta unidade!"
                );

                transaction.rollback();
                return;
            }

            session.persist(vinculo);

            transaction.commit();

            System.out.println("Vínculo cadastrado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Erro ao cadastrar vínculo:");
            e.printStackTrace();
        }
    }

    public List<UsuarioUnidade> listarTodos() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT uu FROM UsuarioUnidade uu " +
                            "JOIN FETCH uu.usuario " +
                            "JOIN FETCH uu.unidade",
                    UsuarioUnidade.class
            ).list();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar vínculos.", e);
        }
    }

    // Buscar vínculo pelo ID

    public UsuarioUnidade buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            return session.createQuery(
                            "SELECT uu FROM UsuarioUnidade uu " +
                                    "JOIN FETCH uu.usuario " +
                                    "JOIN FETCH uu.unidade " +
                                    "WHERE uu.id = :id",
                            UsuarioUnidade.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }


    // Atualizar vínculo
    public void atualizar(UsuarioUnidade vinculo) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            // Verificar se já existe outro vínculo com o mesmo usuário e unidade
            Long quantidade = session.createQuery(
                            "SELECT COUNT(uu) FROM UsuarioUnidade uu " +
                                    "WHERE uu.usuario.id = :idUsuario " +
                                    "AND uu.unidade.id = :idUnidade " +
                                    "AND uu.id <> :idVinculo",
                            Long.class
                    )
                    .setParameter("idUsuario", vinculo.getUsuario().getId())
                    .setParameter("idUnidade", vinculo.getUnidade().getId())
                    .setParameter("idVinculo", vinculo.getId())
                    .uniqueResult();

            // Impedir atualização que gere duplicidade
            if (quantidade != null && quantidade > 0) {

                System.out.println(
                        "Não é possível atualizar: já existe outro vínculo " +
                                "para este usuário e esta unidade!"
                );

                transaction.rollback();
                return;
            }

            session.merge(vinculo);

            transaction.commit();

            System.out.println("Vínculo atualizado com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Erro ao atualizar vínculo:");
            e.printStackTrace();
        }
    }


    // Excluir vínculo
    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            transaction = session.beginTransaction();

            UsuarioUnidade vinculo =
                    session.find(UsuarioUnidade.class, id);

            if (vinculo != null) {
                session.remove(vinculo);
                System.out.println("Vínculo excluído com sucesso!");
            } else {
                System.out.println("Vínculo não encontrado.");
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            System.err.println("Erro ao excluir vínculo:");
            e.printStackTrace();
        }
    }
}
