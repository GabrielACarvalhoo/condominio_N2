
package gestao.persistence.teste.usuarioUnidade;

import gestao.persistence.dao.UsuarioUnidadeDAO;
import gestao.persistence.entity.UsuarioUnidade;
import gestao.persistence.util.HibernateUtil;

public class TesteAtualizarUsuarioUnidade {

    public static void main(String[] args) {

        UsuarioUnidadeDAO vinculoDAO = new UsuarioUnidadeDAO();

        try {
            UsuarioUnidade vinculo = vinculoDAO.buscarPorId(1);

            if (vinculo != null) {

                vinculo.setResponsavelFinanceiro(false);

                vinculoDAO.atualizar(vinculo);

            } else {
                System.out.println("Vínculo não encontrado.");
            }

        } finally {
            HibernateUtil.fechar();
        }
    }
}
