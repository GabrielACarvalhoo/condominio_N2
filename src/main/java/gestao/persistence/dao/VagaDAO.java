
package gestao.persistence.dao;

import gestao.persistence.entity.Vaga;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VagaDAO {

    public void salvar(Vaga vaga) {
        Transaction transacao = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transacao = session.beginTransaction();

            session.persist(vaga);

            transacao.commit();

            System.out.println("Vaga cadastrada com sucesso!");

        } catch (Exception e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }

            System.out.println("Erro ao cadastrar vaga: " + e.getMessage());
        }
    }

    public List<Vaga> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT v FROM Vaga v LEFT JOIN FETCH v.unidade",
                    Vaga.class
            ).getResultList();

        } catch (Exception e) {
            System.out.println("Erro ao listar vagas: " + e.getMessage());
            return List.of();
        }
    }
}
