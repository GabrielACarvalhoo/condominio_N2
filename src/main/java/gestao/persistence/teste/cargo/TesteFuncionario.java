
package gestao.teste;

import gestao.persistence.dao.FuncionarioDAO;
import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.dao.CargoDAO;
import gestao.persistence.entity.Funcionario;
import gestao.persistence.entity.Usuario;
import gestao.persistence.entity.Cargo;
import gestao.persistence.util.HibernateUtil;

import java.util.List;

public class TesteFuncionario {

    public static void main(String[] args) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CargoDAO cargoDAO = new CargoDAO();

        try {

            // 1. LISTAR USUÁRIOS
            System.out.println("\n=== USUÁRIOS DISPONÍVEIS ===");

            List<Usuario> usuarios = usuarioDAO.listarTodos();

            for (Usuario usuario : usuarios) {
                System.out.println(
                        "ID: " + usuario.getId()
                                + " | Nome: " + usuario.getNome()
                );
            }

            // 2. LISTAR CARGOS
            System.out.println("\n=== CARGOS DISPONÍVEIS ===");

            List<Cargo> cargos = cargoDAO.listarTodos();

            for (Cargo cargo : cargos) {
                System.out.println(
                        "ID: " + cargo.getId()
                                + " | Nome: " + cargo.getNome()
                );
            }

            // 3. LISTAR FUNCIONÁRIOS
            System.out.println("\n=== FUNCIONÁRIOS CADASTRADOS ===");

            List<Funcionario> funcionarios =
                    funcionarioDAO.listarTodos();

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
            } else {
                for (Funcionario funcionario : funcionarios) {

                    System.out.println("------------------------------");
                    System.out.println("ID: " + funcionario.getId());
                    System.out.println(
                            "Usuário: " + funcionario.getUsuario().getNome()
                    );
                    System.out.println(
                            "Cargo: " + funcionario.getCargo().getNome()
                    );
                    System.out.println(
                            "Admissão: " + funcionario.getDataAdmissao()
                    );
                    System.out.println(
                            "Demissão: " + funcionario.getDataDemissao()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            HibernateUtil.fechar();
        }
    }
}
