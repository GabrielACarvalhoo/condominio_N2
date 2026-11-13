
package gestao.persistence.dao;

import gestao.persistence.entity.*;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class SolicitacaoManutencaoDAO {

    // REGISTRAR SOLICITAÇÃO
    public Integer salvar(
            Integer idUsuarioSolicitante,
            Integer idUnidade,
            String localProblema,
            String titulo,
            String descricao,
            PrioridadeManutencao prioridade) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Usuario usuario = session.get(Usuario.class, idUsuarioSolicitante);

            if (usuario == null) {
                throw new IllegalArgumentException("Usuário solicitante não encontrado.");
            }

            UnidadeEntity unidade = null;

            if (idUnidade != null) {
                unidade = session.get(UnidadeEntity.class, idUnidade);

                if (unidade == null) {
                    throw new IllegalArgumentException("Unidade não encontrada.");
                }
            }

            if (localProblema == null || localProblema.isBlank()
                    || titulo == null || titulo.isBlank()
                    || descricao == null || descricao.isBlank()
                    || prioridade == null) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
            }

            SolicitacaoManutencao solicitacao = new SolicitacaoManutencao();

            solicitacao.setUsuarioSolicitante(usuario);
            solicitacao.setUnidade(unidade);
            solicitacao.setLocalProblema(localProblema);
            solicitacao.setTitulo(titulo);
            solicitacao.setDescricao(descricao);
            solicitacao.setPrioridade(prioridade);
            solicitacao.setStatus(StatusSolicitacao.ABERTA);

            session.persist(solicitacao);

            transaction.commit();

            return solicitacao.getId();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // LISTAR TODAS AS SOLICITAÇÕES
    public List<SolicitacaoManutencao> listarTodos() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT s FROM SolicitacaoManutencao s " +
                            "JOIN FETCH s.usuarioSolicitante " +
                            "LEFT JOIN FETCH s.unidade " +
                            "LEFT JOIN FETCH s.funcionarioResponsavel f " +
                            "LEFT JOIN FETCH f.usuario " +
                            "ORDER BY s.id DESC",
                    SolicitacaoManutencao.class
            ).getResultList();
        }
    }

    // BUSCAR SOLICITAÇÃO POR ID
    public SolicitacaoManutencao buscarPorId(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT s FROM SolicitacaoManutencao s " +
                                    "JOIN FETCH s.usuarioSolicitante " +
                                    "LEFT JOIN FETCH s.unidade " +
                                    "LEFT JOIN FETCH s.funcionarioResponsavel f " +
                                    "LEFT JOIN FETCH f.usuario " +
                                    "WHERE s.id = :id",
                            SolicitacaoManutencao.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    // ATUALIZAR STATUS
    public boolean atualizarStatus(Integer id, StatusSolicitacao novoStatus) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            SolicitacaoManutencao solicitacao =
                    session.get(SolicitacaoManutencao.class, id);

            if (solicitacao == null || novoStatus == null) {
                return false;
            }

            if (solicitacao.getStatus() == StatusSolicitacao.CANCELADA
                    || solicitacao.getStatus() == StatusSolicitacao.CONCLUIDA) {
                return false;
            }

            solicitacao.setStatus(novoStatus);

            if (novoStatus == StatusSolicitacao.CONCLUIDA) {
                solicitacao.setConcluidaEm(LocalDateTime.now());
            }

            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // ATRIBUIR FUNCIONÁRIO RESPONSÁVEL
    public boolean atribuirResponsavel(
            Integer idSolicitacao,
            Integer idFuncionario) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            SolicitacaoManutencao solicitacao =
                    session.get(SolicitacaoManutencao.class, idSolicitacao);

            Funcionario funcionario =
                    session.get(Funcionario.class, idFuncionario);

            if (solicitacao == null || funcionario == null) {
                return false;
            }

            if (solicitacao.getStatus() == StatusSolicitacao.CONCLUIDA
                    || solicitacao.getStatus() == StatusSolicitacao.CANCELADA) {
                return false;
            }

            solicitacao.setFuncionarioResponsavel(funcionario);

            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // EXCLUIR SOLICITAÇÃO
    public boolean excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            SolicitacaoManutencao solicitacao =
                    session.get(SolicitacaoManutencao.class, id);

            if (solicitacao == null) {
                return false;
            }

            session.remove(solicitacao);

            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
