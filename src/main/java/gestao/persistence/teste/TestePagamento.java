
package gestao.persistence.teste;

import gestao.persistence.dao.PagamentoDAO;
import gestao.persistence.dao.TaxaCondominialDAO;
import gestao.persistence.entity.FormaPagamento;
import gestao.persistence.entity.Pagamento;
import gestao.persistence.entity.StatusTaxaCondominial;
import gestao.persistence.entity.TaxaCondominial;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestePagamento {

    private static final PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private static final TaxaCondominialDAO taxaDAO = new TaxaCondominialDAO();

    private static Integer idTaxaTeste;
    private static Integer idPagamento1;
    private static Integer idPagamento2;

    private static int aprovados = 0;
    private static int falhas = 0;

    public static void main(String[] args) {

        System.out.println("\n========================================");
        System.out.println("       TESTES DO MODULO PAGAMENTO");
        System.out.println("========================================");

        try {
            criarTaxaDeTeste();

            testarCadastroPagamentoParcial();
            testarConsultaPagamento();
            testarPagamentoRestante();
            testarBloqueioPagamentoTaxaQuitada();
            testarExclusaoPagamento();
            testarExclusaoUltimoPagamento();

        } catch (Exception e) {
            falhas++;
            System.out.println("\nERRO DURANTE OS TESTES: " + e.getMessage());
            e.printStackTrace();

        } finally {
            limparDadosDeTeste();
            exibirResultado();
            HibernateUtil.getSessionFactory().close();
        }
    }

    private static void criarTaxaDeTeste() {

        System.out.println("\n[PREPARAÇÃO] Criando taxa exclusiva para os testes...");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                UnidadeEntity unidade = session.find(UnidadeEntity.class, 2);

                if (unidade == null) {
                    throw new IllegalStateException(
                            "Unidade ID 2 não encontrada. Escolha uma unidade existente."
                    );
                }

                TaxaCondominial taxa = new TaxaCondominial();

                taxa.setUnidade(unidade);
                taxa.setDescricao("TESTE AUTOMATIZADO PAGAMENTO");
                taxa.setCompetencia("2099-01");
                taxa.setValor(new BigDecimal("200.00"));
                taxa.setVencimento(LocalDate.now().plusDays(30));
                taxa.setStatus(StatusTaxaCondominial.ABERTA);

                session.persist(taxa);
                transaction.commit();

                idTaxaTeste = taxa.getId();

                System.out.println("Taxa criada com sucesso!");
                System.out.println("ID da taxa: " + idTaxaTeste);

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    private static void testarCadastroPagamentoParcial() {

        executarTeste("Cadastro de pagamento parcial", () -> {

            Pagamento pagamento = criarPagamento(
                    idTaxaTeste,
                    "100.00",
                    FormaPagamento.PIX
            );

            pagamentoDAO.salvar(pagamento);

            idPagamento1 = pagamento.getId();

            TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxaTeste);

            exigir(
                    taxa.getStatus() == StatusTaxaCondominial.PARCIAL,
                    "O status deveria ser PARCIAL."
            );

            exigir(
                    idPagamento1 != null,
                    "O ID do pagamento não foi gerado."
            );
        });
    }

    private static void testarConsultaPagamento() {

        executarTeste("Consulta de pagamentos", () -> {

            Pagamento pagamento = pagamentoDAO.buscarPorId(idPagamento1);

            exigir(
                    pagamento != null,
                    "Pagamento não encontrado pelo ID."
            );

            List<Pagamento> pagamentos =
                    pagamentoDAO.listarPorTaxa(idTaxaTeste);

            exigir(
                    pagamentos.size() == 1,
                    "Deveria existir exatamente um pagamento."
            );

            exigir(
                    pagamentoDAO.listarTodos().stream()
                            .anyMatch(p -> p.getId().equals(idPagamento1)),
                    "Pagamento não apareceu na listagem geral."
            );
        });
    }

    private static void testarPagamentoRestante() {

        executarTeste("Pagamento do valor restante", () -> {

            Pagamento pagamento = criarPagamento(
                    idTaxaTeste,
                    "100.00",
                    FormaPagamento.BOLETO
            );

            pagamentoDAO.salvar(pagamento);

            idPagamento2 = pagamento.getId();

            TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxaTeste);

            exigir(
                    taxa.getStatus() == StatusTaxaCondominial.PAGA,
                    "A taxa deveria estar PAGA."
            );

            exigir(
                    pagamentoDAO.listarPorTaxa(idTaxaTeste).size() == 2,
                    "Deveriam existir dois pagamentos."
            );
        });
    }

    private static void testarBloqueioPagamentoTaxaQuitada() {

        executarTeste("Bloqueio de pagamento em taxa quitada", () -> {

            Pagamento pagamento = criarPagamento(
                    idTaxaTeste,
                    "50.00",
                    FormaPagamento.PIX
            );

            boolean recusado = false;

            try {
                pagamentoDAO.salvar(pagamento);
            } catch (RuntimeException e) {
                recusado = mensagemContem(e, "totalmente paga");
            }

            exigir(
                    recusado,
                    "O sistema deveria recusar pagamento em taxa quitada."
            );

            exigir(
                    pagamentoDAO.listarPorTaxa(idTaxaTeste).size() == 2,
                    "O pagamento recusado não deveria ser cadastrado."
            );
        });
    }

    private static void testarExclusaoPagamento() {

        executarTeste("Exclusão de um pagamento e retorno para PARCIAL", () -> {

            pagamentoDAO.excluir(idPagamento2);

            TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxaTeste);

            exigir(
                    taxa.getStatus() == StatusTaxaCondominial.PARCIAL,
                    "Após excluir um pagamento, o status deveria voltar para PARCIAL."
            );

            exigir(
                    pagamentoDAO.listarPorTaxa(idTaxaTeste).size() == 1,
                    "Deveria restar apenas um pagamento."
            );
        });
    }

    private static void testarExclusaoUltimoPagamento() {

        executarTeste("Exclusão do último pagamento e retorno para ABERTA", () -> {

            pagamentoDAO.excluir(idPagamento1);

            TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxaTeste);

            exigir(
                    taxa.getStatus() == StatusTaxaCondominial.ABERTA,
                    "Sem pagamentos e antes do vencimento, o status deveria ser ABERTA."
            );

            exigir(
                    pagamentoDAO.listarPorTaxa(idTaxaTeste).isEmpty(),
                    "Não deveria existir nenhum pagamento."
            );
        });
    }

    private static Pagamento criarPagamento(
            Integer idTaxa,
            String valor,
            FormaPagamento forma
    ) {

        Pagamento pagamento = new Pagamento();

        TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxa);

        if (taxa == null) {
            throw new IllegalArgumentException("Taxa de teste não encontrada.");
        }

        pagamento.setTaxa(taxa);
        pagamento.setValorPago(new BigDecimal(valor));
        pagamento.setPagoEm(LocalDateTime.now());
        pagamento.setFormaPagamento(forma);
        pagamento.setObservacoes("Pagamento criado pelo teste automatizado.");

        return pagamento;
    }

    private static boolean mensagemContem(Throwable erro, String texto) {

        Throwable atual = erro;

        while (atual != null) {

            if (atual.getMessage() != null
                    && atual.getMessage().toLowerCase()
                    .contains(texto.toLowerCase())) {
                return true;
            }

            atual = atual.getCause();
        }

        return false;
    }

    private static void executarTeste(String nome, Runnable teste) {

        System.out.println("\n[TESTE] " + nome);

        try {
            teste.run();

            aprovados++;
            System.out.println("RESULTADO: APROVADO");

        } catch (Exception e) {

            falhas++;
            System.out.println("RESULTADO: FALHOU");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    private static void exigir(boolean condicao, String mensagem) {

        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void limparDadosDeTeste() {

        if (idTaxaTeste == null) {
            return;
        }

        System.out.println("\n[LIMPEZA] Removendo registros de teste...");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {

                List<Pagamento> pagamentos = session.createQuery(
                        "SELECT p FROM Pagamento p WHERE p.taxa.id = :id",
                        Pagamento.class
                ).setParameter("id", idTaxaTeste).list();

                for (Pagamento pagamento : pagamentos) {
                    session.remove(pagamento);
                }

                TaxaCondominial taxa =
                        session.get(TaxaCondominial.class, idTaxaTeste);

                if (taxa != null) {
                    session.remove(taxa);
                }

                transaction.commit();

                System.out.println("Registros de teste removidos.");

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                falhas++;
                System.out.println("Falha na limpeza: " + e.getMessage());
            }
        }
    }

    private static void exibirResultado() {

        System.out.println("\n========================================");
        System.out.println("          RESULTADO DOS TESTES");
        System.out.println("========================================");

        System.out.println("Aprovados: " + aprovados);
        System.out.println("Falhas: " + falhas);
        System.out.println("Total: " + (aprovados + falhas));

        if (falhas == 0 && aprovados == 6) {
            System.out.println("\nTODOS OS TESTES FORAM APROVADOS!");
        } else {
            System.out.println("\nATENÇÃO: EXISTEM TESTES COM FALHA.");
        }

        System.out.println("========================================");
    }
}
