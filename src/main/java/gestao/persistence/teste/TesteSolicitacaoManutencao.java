
package gestao.persistence.teste;

import gestao.persistence.dao.SolicitacaoManutencaoDAO;
import gestao.persistence.entity.PrioridadeManutencao;
import gestao.persistence.entity.SolicitacaoManutencao;
import gestao.persistence.entity.StatusSolicitacao;
import gestao.persistence.util.HibernateUtil;

import java.util.List;

public class TesteSolicitacaoManutencao {

    public static void main(String[] args) {

        SolicitacaoManutencaoDAO dao =
                new SolicitacaoManutencaoDAO();

        try {

            // ==========================================
            // 1. CADASTRAR SOLICITAÇÃO
            // ==========================================

            System.out.println("\n========== CADASTRAR SOLICITAÇÃO ==========");

            // Informe um ID de usuário existente no banco.
            Integer idUsuario = 2;

            // Informe um ID de unidade existente ou null.
            Integer idUnidade = null;

            Integer idSolicitacao = dao.salvar(
                    idUsuario,
                    idUnidade,
                    "Área da piscina",
                    "Problema na iluminação",
                    "Uma das luminárias da piscina não está funcionando.",
                    PrioridadeManutencao.MEDIA
            );

            System.out.println(
                    "Solicitação cadastrada com sucesso! ID: "
                            + idSolicitacao
            );


            // ==========================================
            // 2. LISTAR TODAS AS SOLICITAÇÕES
            // ==========================================

            System.out.println("\n========== LISTAR SOLICITAÇÕES ==========");

            List<SolicitacaoManutencao> solicitacoes =
                    dao.listarTodos();

            if (solicitacoes.isEmpty()) {
                System.out.println("Nenhuma solicitação cadastrada.");
            } else {

                for (SolicitacaoManutencao s : solicitacoes) {

                    System.out.println("----------------------------------");
                    System.out.println("ID: " + s.getId());
                    System.out.println("Título: " + s.getTitulo());
                    System.out.println("Local: " + s.getLocalProblema());
                    System.out.println("Descrição: " + s.getDescricao());
                    System.out.println("Prioridade: " + s.getPrioridade());
                    System.out.println("Status: " + s.getStatus());
                    System.out.println(
                            "Solicitante: " +
                                    s.getUsuarioSolicitante().getNome()
                    );

                    if (s.getUnidade() != null) {
                        System.out.println(
                                "Unidade: " +
                                        s.getUnidade().getBloco() + " - " +
                                        s.getUnidade().getNumero()
                        );
                    } else {
                        System.out.println("Unidade: Não informada");
                    }

                    if (s.getFuncionarioResponsavel() != null) {
                        System.out.println(
                                "Responsável: " +
                                        s.getFuncionarioResponsavel()
                                                .getUsuario().getNome()
                        );
                    } else {
                        System.out.println("Responsável: Não atribuído");
                    }
                }
            }


            // ==========================================
            // 3. BUSCAR SOLICITAÇÃO POR ID
            // ==========================================

            System.out.println("\n========== BUSCAR POR ID ==========");

            SolicitacaoManutencao encontrada =
                    dao.buscarPorId(idSolicitacao);

            if (encontrada != null) {

                System.out.println("Solicitação encontrada!");
                System.out.println("ID: " + encontrada.getId());
                System.out.println("Título: " + encontrada.getTitulo());
                System.out.println("Status: " + encontrada.getStatus());

            } else {
                System.out.println("Solicitação não encontrada.");
            }


            // ==========================================
            // 4. ATUALIZAR STATUS
            // ==========================================

            System.out.println("\n========== ATUALIZAR STATUS ==========");

            boolean atualizado = dao.atualizarStatus(
                    idSolicitacao,
                    StatusSolicitacao.EM_ANDAMENTO
            );

            if (atualizado) {
                System.out.println("Status atualizado com sucesso!");
            } else {
                System.out.println("Não foi possível atualizar o status.");
            }


            // ==========================================
            // 5. CONFERIR ALTERAÇÃO
            // ==========================================

            System.out.println("\n========== CONFERIR ALTERAÇÃO ==========");

            SolicitacaoManutencao atualizada =
                    dao.buscarPorId(idSolicitacao);

            if (atualizada != null) {
                System.out.println(
                        "Novo status: " + atualizada.getStatus()
                );
            }

            System.out.println("\n========== TESTE FINALIZADO ==========");

        } catch (Exception e) {

            System.err.println("\nERRO DURANTE O TESTE:");
            e.printStackTrace();

        } finally {

            HibernateUtil.fechar();

        }
    }
}
