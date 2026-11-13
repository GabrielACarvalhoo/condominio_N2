
package gestao.persistence.dao;

import gestao.persistence.entity.FormaPagamento;
import gestao.persistence.entity.Pagamento;
import gestao.persistence.entity.StatusTaxaCondominial;
import gestao.persistence.entity.TaxaCondominial;
import gestao.persistence.util.HibernateUtil;

import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PagamentoDAO {


    public void salvar(Pagamento pagamento) {

        if (pagamento == null || pagamento.getTaxa() == null
                || pagamento.getTaxa().getId() == null) {
            throw new IllegalArgumentException("Pagamento ou taxa inválida.");
        }

        if (pagamento.getValorPago() == null
                || pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor pago deve ser maior que zero.");
        }

        if (pagamento.getPagoEm() == null
                || pagamento.getFormaPagamento() == null) {
            throw new IllegalArgumentException(
                    "Data e forma de pagamento são obrigatórias."
            );
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {

                Integer idTaxa = pagamento.getTaxa().getId();

                TaxaCondominial taxa = session.find(
                        TaxaCondominial.class,
                        idTaxa,
                        LockModeType.PESSIMISTIC_WRITE
                );

                if (taxa == null) {
                    throw new IllegalArgumentException("Taxa não encontrada.");
                }

                if (taxa.getStatus() == StatusTaxaCondominial.CANCELADA) {
                    throw new IllegalArgumentException(
                            "Não é possível pagar uma taxa cancelada."
                    );
                }

                if (taxa.getStatus() == StatusTaxaCondominial.PAGA) {
                    throw new IllegalArgumentException(
                            "Essa taxa já está totalmente paga."
                    );
                }

                BigDecimal totalPago = calcularTotalPago(session, idTaxa);

                BigDecimal novoTotal = totalPago.add(pagamento.getValorPago());

                if (novoTotal.compareTo(taxa.getValor()) > 0) {
                    throw new IllegalArgumentException(
                            "O pagamento ultrapassa o valor restante da taxa."
                    );
                }

                pagamento.setTaxa(taxa);

                session.persist(pagamento);
                session.flush();

                atualizarStatus(session, taxa);

                transaction.commit();

                System.out.println("Pagamento cadastrado com sucesso!");

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(
                        "Erro ao cadastrar pagamento: " + e.getMessage(), e
                );
            }
        }
    }


    public List<Pagamento> listarTodos() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT p FROM Pagamento p " +
                            "JOIN FETCH p.taxa t " +
                            "JOIN FETCH t.unidade " +
                            "ORDER BY p.pagoEm DESC",
                    Pagamento.class
            ).list();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao listar pagamentos: " + e.getMessage(), e
            );
        }
    }

    public Pagamento buscarPorId(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT p FROM Pagamento p " +
                                    "JOIN FETCH p.taxa t " +
                                    "JOIN FETCH t.unidade " +
                                    "WHERE p.id = :id",
                            Pagamento.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao buscar pagamento: " + e.getMessage(), e
            );
        }
    }

    public List<Pagamento> listarPorTaxa(Integer idTaxa) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT p FROM Pagamento p " +
                                    "JOIN FETCH p.taxa t " +
                                    "JOIN FETCH t.unidade " +
                                    "WHERE t.id = :idTaxa " +
                                    "ORDER BY p.pagoEm DESC",
                            Pagamento.class
                    )
                    .setParameter("idTaxa", idTaxa)
                    .list();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao listar pagamentos da taxa: " + e.getMessage(), e
            );
        }
    }


    public void excluir(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {

                Pagamento pagamento = session.get(Pagamento.class, id);

                if (pagamento == null) {
                    throw new IllegalArgumentException("Pagamento não encontrado.");
                }

                Integer idTaxa = pagamento.getTaxa().getId();

                TaxaCondominial taxa = session.find(
                        TaxaCondominial.class,
                        idTaxa,
                        LockModeType.PESSIMISTIC_WRITE
                );

                session.remove(pagamento);
                session.flush();

                atualizarStatus(session, taxa);

                transaction.commit();

                System.out.println("Pagamento excluído com sucesso!");

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(
                        "Erro ao excluir pagamento: " + e.getMessage(), e
                );
            }
        }
    }


    private BigDecimal calcularTotalPago(Session session, Integer idTaxa) {

        BigDecimal total = session.createQuery(
                        "SELECT SUM(p.valorPago) FROM Pagamento p " +
                                "WHERE p.taxa.id = :idTaxa",
                        BigDecimal.class
                )
                .setParameter("idTaxa", idTaxa)
                .uniqueResult();

        return total == null ? BigDecimal.ZERO : total;
    }

    private void atualizarStatus(
            Session session,
            TaxaCondominial taxa
    ) {

        BigDecimal totalPago = calcularTotalPago(session, taxa.getId());

        if (totalPago.compareTo(taxa.getValor()) >= 0) {

            taxa.setStatus(StatusTaxaCondominial.PAGA);

        } else if (totalPago.compareTo(BigDecimal.ZERO) > 0) {

            taxa.setStatus(StatusTaxaCondominial.PARCIAL);

        } else if (taxa.getVencimento().isBefore(LocalDate.now())) {

            taxa.setStatus(StatusTaxaCondominial.VENCIDA);

        } else {

            taxa.setStatus(StatusTaxaCondominial.ABERTA);
        }
    }
}
