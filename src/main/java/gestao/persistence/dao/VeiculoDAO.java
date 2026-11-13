
package gestao.persistence.dao;

import gestao.persistence.entity.*;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VeiculoDAO {

    public void salvar(Veiculo veiculo) {
        Transaction transacao = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transacao = session.beginTransaction();

            // Validações básicas
            if (veiculo.getUnidade() == null || veiculo.getUnidade().getId() == null) {
                throw new IllegalArgumentException("Informe a unidade do veículo.");
            }

            if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
                throw new IllegalArgumentException("Informe a placa do veículo.");
            }

            if (veiculo.getTipo() == null) {
                throw new IllegalArgumentException("Informe o tipo do veículo.");
            }

            // Recupera a unidade gerenciada pelo Hibernate
            UnidadeEntity unidade = session.find(
                    UnidadeEntity.class,
                    veiculo.getUnidade().getId()
            );

            if (unidade == null) {
                throw new IllegalArgumentException("Unidade não encontrada.");
            }

            veiculo.setUnidade(unidade);

            // Recupera o usuário, caso tenha sido informado
            if (veiculo.getUsuario() != null) {
                Usuario usuario = session.find(
                        Usuario.class,
                        veiculo.getUsuario().getId()
                );

                if (usuario == null) {
                    throw new IllegalArgumentException("Usuário não encontrado.");
                }

                veiculo.setUsuario(usuario);
            }

            // Valida e associa a vaga, caso tenha sido informada
            if (veiculo.getVaga() != null) {
                Vaga vaga = session.find(
                        Vaga.class,
                        veiculo.getVaga().getId()
                );

                if (vaga == null) {
                    throw new IllegalArgumentException("Vaga não encontrada.");
                }

                if (vaga.getStatus() != StatusVaga.DISPONIVEL) {
                    throw new IllegalArgumentException(
                            "A vaga não está disponível."
                    );
                }

                if (!vaga.getTipo().name().equals(veiculo.getTipo().name())) {
                    throw new IllegalArgumentException(
                            "O tipo do veículo não corresponde ao tipo da vaga."
                    );
                }

                if (vaga.getUnidade() != null
                        && !vaga.getUnidade().getId().equals(unidade.getId())) {
                    throw new IllegalArgumentException(
                            "A vaga pertence a outra unidade."
                    );
                }

                veiculo.setVaga(vaga);
                vaga.setStatus(StatusVaga.VINCULADA);
            }

            session.persist(veiculo);
            transacao.commit();

            System.out.println("Veículo cadastrado com sucesso!");

        } catch (Exception e) {
            if (transacao != null && transacao.isActive()) {
                transacao.rollback();
            }

            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    public List<Veiculo> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT DISTINCT v FROM Veiculo v " +
                            "JOIN FETCH v.unidade " +
                            "LEFT JOIN FETCH v.usuario " +
                            "LEFT JOIN FETCH v.vaga",
                    Veiculo.class
            ).getResultList();

        } catch (Exception e) {
            System.out.println("Erro ao listar veículos: " + e.getMessage());
            return List.of();
        }
    }
}
