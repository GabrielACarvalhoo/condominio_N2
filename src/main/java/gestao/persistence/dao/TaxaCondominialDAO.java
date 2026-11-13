
package gestao.persistence.dao;

import gestao.persistence.entity.TaxaCondominial;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaxaCondominialDAO {

    // ==========================================
    // CADASTRAR TAXA
    // ==========================================

    public void salvar(TaxaCondominial taxa) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            if (taxa == null) {
                throw new IllegalArgumentException(
                        "A taxa condominial não pode ser nula."
                );
            }

            if (taxa.getUnidade() == null ||
                    taxa.getUnidade().getId() == null) {
                throw new IllegalArgumentException(
                        "É necessário informar uma unidade válida."
                );
            }

            if (taxa.getValor() == null ||
                    taxa.getValor().signum() <= 0) {
                throw new IllegalArgumentException(
                        "O valor da taxa deve ser maior que zero."
                );
            }

            if (taxa.getDescricao() == null ||
                    taxa.getDescricao().isBlank()) {
                throw new IllegalArgumentException(
                        "A descrição da taxa é obrigatória."
                );
            }

            if (taxa.getCompetencia() == null ||
                    !taxa.getCompetencia().matches("\\d{4}-(0[1-9]|1[0-2])")) {
                throw new IllegalArgumentException(
                        "A competência deve seguir o formato AAAA-MM."
                );
            }

            if (taxa.getVencimento() == null) {
                throw new IllegalArgumentException(
                        "A data de vencimento é obrigatória."
                );
            }

            Long quantidade = session.createQuery(
                            "SELECT COUNT(t) FROM TaxaCondominial t " +
                                    "WHERE t.unidade.id = :idUnidade " +
                                    "AND t.competencia = :competencia " +
                                    "AND t.descricao = :descricao",
                            Long.class
                    )
                    .setParameter("idUnidade", taxa.getUnidade().getId())
                    .setParameter("competencia", taxa.getCompetencia())
                    .setParameter("descricao", taxa.getDescricao())
                    .uniqueResult();

            if (quantidade != null && quantidade > 0) {
                throw new IllegalArgumentException(
                        "Já existe uma taxa com essa descrição " +
                                "para essa unidade e competência."
                );
            }

            session.persist(taxa);

            transaction.commit();

            System.out.println("Taxa condominial cadastrada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao cadastrar taxa condominial: " +
                            e.getMessage(), e
            );
        }
    }

    // ==========================================
    // LISTAR TODAS AS TAXAS
    // ==========================================

    public List<TaxaCondominial> listarTodos() {

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT t FROM TaxaCondominial t " +
                            "JOIN FETCH t.unidade " +
                            "ORDER BY t.vencimento DESC",
                    TaxaCondominial.class
            ).list();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao listar taxas condominiais: " +
                            e.getMessage(), e
            );
        }
    }

    // ==========================================
    // BUSCAR TAXA POR ID
    // ==========================================

    public TaxaCondominial buscarPorId(Integer id) {

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT t FROM TaxaCondominial t " +
                                    "JOIN FETCH t.unidade " +
                                    "WHERE t.id = :id",
                            TaxaCondominial.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao buscar taxa condominial: " +
                            e.getMessage(), e
            );
        }
    }

    // ==========================================
    // LISTAR TAXAS DE UMA UNIDADE
    // ==========================================

    public List<TaxaCondominial> listarPorUnidade(Integer idUnidade) {

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT t FROM TaxaCondominial t " +
                                    "JOIN FETCH t.unidade u " +
                                    "WHERE u.id = :idUnidade " +
                                    "ORDER BY t.vencimento DESC",
                            TaxaCondominial.class
                    )
                    .setParameter("idUnidade", idUnidade)
                    .list();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao listar taxas da unidade: " +
                            e.getMessage(), e
            );
        }
    }

    // ==========================================
    // ATUALIZAR TAXA
    // ==========================================

    public void atualizar(TaxaCondominial taxa) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            if (taxa == null || taxa.getId() == null) {
                throw new IllegalArgumentException(
                        "Informe uma taxa válida para atualização."
                );
            }

            TaxaCondominial existente = session.get(
                    TaxaCondominial.class,
                    taxa.getId()
            );

            if (existente == null) {
                throw new IllegalArgumentException(
                        "Taxa condominial não encontrada."
                );
            }

            existente.setDescricao(taxa.getDescricao());
            existente.setCompetencia(taxa.getCompetencia());
            existente.setValor(taxa.getValor());
            existente.setVencimento(taxa.getVencimento());
            existente.setStatus(taxa.getStatus());

            transaction.commit();

            System.out.println("Taxa atualizada com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao atualizar taxa condominial: " +
                            e.getMessage(), e
            );
        }
    }

    // ==========================================
    // EXCLUIR TAXA
    // ==========================================

    public void excluir(Integer id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            TaxaCondominial taxa = session.get(
                    TaxaCondominial.class,
                    id
            );

            if (taxa == null) {
                throw new IllegalArgumentException(
                        "Taxa condominial não encontrada."
                );
            }

            session.remove(taxa);

            transaction.commit();

            System.out.println("Taxa excluída com sucesso!");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException(
                    "Erro ao excluir taxa condominial: " +
                            e.getMessage(), e
            );
        }
    }
}
