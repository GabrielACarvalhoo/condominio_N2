
package gestao.persistence.dao;

import gestao.persistence.entity.Encomenda;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.entity.Funcionario;
import gestao.persistence.entity.StatusEncomenda;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EncomendaDAO {

    public void salvar(Encomenda encomenda) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            UnidadeEntity unidade = session.find(
                    UnidadeEntity.class,
                    encomenda.getUnidade().getId()
            );

            if (unidade == null) {
                throw new IllegalArgumentException("Unidade não encontrada.");
            }

            encomenda.setUnidade(unidade);

            if (encomenda.getFuncionarioRecebimento() != null) {
                Funcionario funcionario = session.find(
                        Funcionario.class,
                        encomenda.getFuncionarioRecebimento().getId()
                );

                if (funcionario == null) {
                    throw new IllegalArgumentException("Funcionário não encontrado.");
                }

                encomenda.setFuncionarioRecebimento(funcionario);
            }

            encomenda.setStatus(StatusEncomenda.RECEBIDA);

            session.persist(encomenda);
            transaction.commit();

            System.out.println("Encomenda registrada com sucesso!");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Encomenda> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT e FROM Encomenda e " +
                            "JOIN FETCH e.unidade " +
                            "LEFT JOIN FETCH e.funcionarioRecebimento " +
                            "ORDER BY e.recebidaEm DESC",
                    Encomenda.class
            ).list();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao listar encomendas: " + e.getMessage(), e
            );
        }
    }

    public void registrarRetirada(Integer idEncomenda, String retiradoPor) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Encomenda encomenda = session.find(Encomenda.class, idEncomenda);

            if (encomenda == null) {
                throw new IllegalArgumentException("Encomenda não encontrada.");
            }

            if (encomenda.getStatus() != StatusEncomenda.RECEBIDA) {
                throw new IllegalArgumentException(
                        "Somente encomendas recebidas podem ser retiradas."
                );
            }

            if (retiradoPor == null || retiradoPor.isBlank()) {
                throw new IllegalArgumentException(
                        "Informe o nome de quem retirou a encomenda."
                );
            }

            encomenda.setRetiradoPor(retiradoPor);
            encomenda.setRetiradaEm(java.time.LocalDateTime.now());
            encomenda.setStatus(StatusEncomenda.RETIRADA);

            transaction.commit();

            System.out.println("Retirada registrada com sucesso!");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

}
