
package gestao.persistence.dao;

import gestao.persistence.entity.Reserva;
import gestao.persistence.entity.EspacoComum;
import gestao.persistence.entity.Usuario;
import gestao.persistence.entity.StatusReserva;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class   ReservaDAO {

    // Registra uma nova solicitação de reserva
    public void solicitar(Reserva reserva) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (reserva.getInicio() == null || reserva.getFim() == null
                    || !reserva.getFim().isAfter(reserva.getInicio())) {
                throw new IllegalArgumentException("Período da reserva inválido.");
            }

            EspacoComum espaco = session.find(
                    EspacoComum.class, reserva.getEspaco().getId());

            Usuario solicitante = session.find(
                    Usuario.class, reserva.getUsuarioSolicitante().getId());

            if (espaco == null || !Boolean.TRUE.equals(espaco.getAtivo())) {
                throw new IllegalArgumentException("Espaço não encontrado ou inativo.");
            }

            if (solicitante == null) {
                throw new IllegalArgumentException("Usuário solicitante não encontrado.");
            }

            if (reserva.getQuantidadePessoas() != null &&
                    (reserva.getQuantidadePessoas() <= 0 ||
                            reserva.getQuantidadePessoas() > espaco.getCapacidade())) {
                throw new IllegalArgumentException("Quantidade de pessoas inválida para este espaço.");
            }

            reserva.setEspaco(espaco);
            reserva.setUsuarioSolicitante(solicitante);
            reserva.setStatus(StatusReserva.PENDENTE);

            session.persist(reserva);
            transaction.commit();

            System.out.println("Solicitação de reserva registrada com sucesso!");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Lista todas as reservas
    public List<Reserva> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT r FROM Reserva r " +
                            "JOIN FETCH r.espaco " +
                            "JOIN FETCH r.usuarioSolicitante " +
                            "LEFT JOIN FETCH r.usuarioAprovador " +
                            "ORDER BY r.inicio",
                    Reserva.class
            ).list();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar reservas: " + e.getMessage(), e);
        }
    }

    // Aprova ou cancela uma solicitação pendente
    public void alterarStatus(Integer idReserva, StatusReserva novoStatus,
                              Integer idUsuarioAprovador) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (novoStatus != StatusReserva.APROVADA &&
                    novoStatus != StatusReserva.CANCELADA) {
                throw new IllegalArgumentException("Status não permitido nesta operação.");
            }

            Reserva reserva = session.createQuery(
                    "SELECT r FROM Reserva r " +
                            "JOIN FETCH r.espaco " +
                            "JOIN FETCH r.usuarioSolicitante " +
                            "WHERE r.id = :id",
                    Reserva.class
            ).setParameter("id", idReserva).uniqueResult();

            if (reserva == null) {
                throw new IllegalArgumentException("Reserva não encontrada.");
            }

            if (reserva.getStatus() != StatusReserva.PENDENTE) {
                throw new IllegalArgumentException("A reserva não está pendente.");
            }

            Usuario aprovador = session.find(Usuario.class, idUsuarioAprovador);

            if (aprovador == null) {
                throw new IllegalArgumentException("Usuário responsável não encontrado.");
            }

            if (novoStatus == StatusReserva.APROVADA) {

                Long conflitos = session.createQuery(
                                "SELECT COUNT(r) FROM Reserva r " +
                                        "WHERE r.espaco.id = :idEspaco " +
                                        "AND r.status = :status " +
                                        "AND r.inicio < :fim " +
                                        "AND r.fim > :inicio",
                                Long.class
                        )
                        .setParameter("idEspaco", reserva.getEspaco().getId())
                        .setParameter("status", StatusReserva.APROVADA)
                        .setParameter("inicio", reserva.getInicio())
                        .setParameter("fim", reserva.getFim())
                        .uniqueResult();

                if (conflitos != null && conflitos > 0) {
                    throw new IllegalArgumentException(
                            "Já existe uma reserva aprovada neste horário.");
                }
            }

            reserva.setStatus(novoStatus);
            reserva.setUsuarioAprovador(aprovador);

            transaction.commit();

            System.out.println("Status da reserva atualizado com sucesso!");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void excluir(Integer idReserva) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Reserva reserva = session.find(Reserva.class, idReserva);

            if (reserva == null) {
                throw new IllegalArgumentException("Reserva não encontrada.");
            }

            session.remove(reserva);

            transaction.commit();

            System.out.println("Reserva excluída com sucesso!");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

}
