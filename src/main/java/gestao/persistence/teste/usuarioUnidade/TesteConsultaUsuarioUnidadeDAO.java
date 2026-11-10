
package gestao.persistence.teste.usuarioUnidade;

import gestao.persistence.dao.UsuarioUnidadeDAO;
import gestao.persistence.entity.UsuarioUnidade;
import gestao.persistence.util.HibernateUtil;

import java.util.List;

public class TesteConsultaUsuarioUnidadeDAO {

    public static void main(String[] args) {

        UsuarioUnidadeDAO vinculoDAO = new UsuarioUnidadeDAO();

        try {
            System.out.println("=== TODOS OS VINCULOS ===");

            List<UsuarioUnidade> vinculos = vinculoDAO.listarTodos();

            for (UsuarioUnidade vinculo : vinculos) {
                System.out.println(
                        "ID: " + vinculo.getId() +
                                " | Tipo: " + vinculo.getTipoVinculo() +
                                " | Início: " + vinculo.getDataInicio() +
                                " | Responsável financeiro: " +
                                vinculo.getResponsavelFinanceiro()
                );
            }

            System.out.println("\n=== BUSCA POR ID ===");

            UsuarioUnidade vinculo = vinculoDAO.buscarPorId(1);

            if (vinculo != null) {
                System.out.println(
                        "Vínculo encontrado: " +
                                vinculo.getTipoVinculo()
                );
            } else {
                System.out.println("Vínculo não encontrado.");
            }

        } finally {
            HibernateUtil.fechar();
        }
    }
}
