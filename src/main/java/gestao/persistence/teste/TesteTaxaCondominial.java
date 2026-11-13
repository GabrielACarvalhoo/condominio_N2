
package gestao.persistence.teste;

import gestao.persistence.dao.TaxaCondominialDAO;
import gestao.persistence.entity.TaxaCondominial;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;

import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TesteTaxaCondominial {

    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);
        TaxaCondominialDAO taxaDAO = new TaxaCondominialDAO();

        try {

            // ==========================================
            // LISTAR UNIDADES DISPONÍVEIS
            // ==========================================

            System.out.println("\n--- UNIDADES DISPONÍVEIS ---");

            try (Session session = HibernateUtil
                    .getSessionFactory().openSession()) {

                List<UnidadeEntity> unidades = session.createQuery(
                        "FROM UnidadeEntity ORDER BY bloco, numero",
                        UnidadeEntity.class
                ).list();

                if (unidades.isEmpty()) {
                    System.out.println("Nenhuma unidade cadastrada.");
                    return;
                }

                for (UnidadeEntity unidade : unidades) {

                    System.out.println(
                            "ID: " + unidade.getId()
                                    + " | Bloco: " + unidade.getBloco()
                                    + " | Número: " + unidade.getNumero()
                    );
                }
            }

            // ==========================================
            // SELECIONAR UNIDADE
            // ==========================================

            System.out.print("\nDigite o ID da unidade: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade;

            try (Session session = HibernateUtil
                    .getSessionFactory().openSession()) {

                unidade = session.get(UnidadeEntity.class, idUnidade);
            }

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            // ==========================================
            // CADASTRAR TAXA
            // ==========================================

            System.out.println("\n--- CADASTRAR TAXA ---");

            System.out.print("Descrição: ");
            String descricao = leitura.nextLine();

            System.out.print("Competência (AAAA-MM): ");
            String competencia = leitura.nextLine();

            System.out.print("Valor: ");
            BigDecimal valor = new BigDecimal(
                    leitura.nextLine().replace(",", ".")
            );

            System.out.print("Vencimento (AAAA-MM-DD): ");
            LocalDate vencimento = LocalDate.parse(leitura.nextLine());

            TaxaCondominial taxa = new TaxaCondominial(
                    unidade,
                    descricao,
                    competencia,
                    valor,
                    vencimento
            );

            taxaDAO.salvar(taxa);

            // ==========================================
            // LISTAR TAXAS
            // ==========================================

            System.out.println("\n--- TODAS AS TAXAS ---");

            List<TaxaCondominial> taxas = taxaDAO.listarTodos();

            for (TaxaCondominial t : taxas) {

                System.out.println("------------------------------");
                System.out.println("ID: " + t.getId());
                System.out.println("Unidade: "
                        + t.getUnidade().getBloco()
                        + " / " + t.getUnidade().getNumero());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println("Competência: " + t.getCompetencia());
                System.out.println("Valor: R$ " + t.getValor());
                System.out.println("Vencimento: " + t.getVencimento());
                System.out.println("Status: " + t.getStatus());
            }

            // ==========================================
            // BUSCAR TAXA CADASTRADA
            // ==========================================

            System.out.print("\nDigite o ID da taxa para consultar: ");
            Integer idTaxa = Integer.parseInt(leitura.nextLine());

            TaxaCondominial encontrada = taxaDAO.buscarPorId(idTaxa);

            if (encontrada != null) {

                System.out.println("\nTaxa encontrada!");
                System.out.println("Descrição: " + encontrada.getDescricao());
                System.out.println("Valor: R$ " + encontrada.getValor());
                System.out.println("Status: " + encontrada.getStatus());

            } else {

                System.out.println("Taxa não encontrada.");
            }

            // ==========================================
            // LISTAR TAXAS DA UNIDADE
            // ==========================================

            System.out.println("\n--- TAXAS DA UNIDADE ---");

            List<TaxaCondominial> taxasUnidade =
                    taxaDAO.listarPorUnidade(idUnidade);

            for (TaxaCondominial t : taxasUnidade) {

                System.out.println(
                        t.getId() + " - "
                                + t.getDescricao() + " - R$ "
                                + t.getValor() + " - "
                                + t.getStatus()
                );
            }

            System.out.println("\nTeste finalizado!");

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um número válido.");

        } catch (Exception e) {

            System.out.println("Erro durante o teste: " + e.getMessage());
            e.printStackTrace();

        } finally {

            HibernateUtil.fechar();
            leitura.close();
        }
    }
}
