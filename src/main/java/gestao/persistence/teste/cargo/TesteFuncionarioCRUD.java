
package gestao.teste;

import gestao.persistence.dao.FuncionarioDAO;
import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.dao.CargoDAO;
import gestao.persistence.entity.Funcionario;
import gestao.persistence.entity.Usuario;
import gestao.persistence.entity.Cargo;
import gestao.persistence.util.HibernateUtil;

import java.time.LocalDate;

public class TesteFuncionarioCRUD {

    public static void main(String[] args) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CargoDAO cargoDAO = new CargoDAO();

        Integer idFuncionario = null;

        try {
            // 1. Buscar usuário e cargo existentes
            Usuario usuario = usuarioDAO.buscarPorId(2);
            Cargo cargo = cargoDAO.buscarPorId(1);

            if (usuario == null || cargo == null) {
                throw new RuntimeException("Usuário ou cargo não encontrado.");
            }

            System.out.println("\n=== DADOS SELECIONADOS ===");
            System.out.println("Usuário: " + usuario.getNome());
            System.out.println("Cargo: " + cargo.getNome());

            // 2. Cadastrar funcionário
            Funcionario funcionario = new Funcionario(
                    usuario,
                    cargo,
                    LocalDate.now(),
                    null
            );

            funcionarioDAO.salvar(funcionario);
            idFuncionario = funcionario.getId();

            System.out.println("\n=== CADASTRO REALIZADO ===");
            System.out.println("ID do funcionário: " + idFuncionario);

            // 3. Consultar funcionário
            Funcionario consultado =
                    funcionarioDAO.buscarPorId(idFuncionario);

            System.out.println("\n=== FUNCIONÁRIO CONSULTADO ===");
            System.out.println("ID: " + consultado.getId());
            System.out.println("Usuário: " + consultado.getUsuario().getNome());
            System.out.println("Cargo: " + consultado.getCargo().getNome());
            System.out.println("Admissão: " + consultado.getDataAdmissao());
            System.out.println("Demissão: " + consultado.getDataDemissao());

            // 4. Atualizar data de demissão
            consultado.setDataDemissao(LocalDate.now().plusDays(30));
            funcionarioDAO.atualizar(consultado);

            System.out.println("\n=== FUNCIONÁRIO ATUALIZADO ===");

            Funcionario atualizado =
                    funcionarioDAO.buscarPorId(idFuncionario);

            System.out.println("Nova data de demissão: "
                    + atualizado.getDataDemissao());

            // 5. Listar funcionários
            System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===");

            for (Funcionario f : funcionarioDAO.listarTodos()) {
                System.out.println(
                        "ID: " + f.getId()
                                + " | Usuário: " + f.getUsuario().getNome()
                                + " | Cargo: " + f.getCargo().getNome()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            // 6. Excluir o registro de teste
            if (idFuncionario != null) {
                try {
                    funcionarioDAO.excluir(idFuncionario);
                    System.out.println("\n=== EXCLUSÃO REALIZADA ===");

                    Funcionario removido =
                            funcionarioDAO.buscarPorId(idFuncionario);

                    System.out.println(
                            removido == null
                                    ? "Funcionário removido com sucesso."
                                    : "O funcionário ainda existe."
                    );

                } catch (Exception e) {
                    System.out.println("Erro ao excluir funcionário de teste:");
                    e.printStackTrace();
                }
            }

            HibernateUtil.fechar();
        }
    }
}
