
package gestao.persistence.teste.usuarioUnidade;

import gestao.persistence.dao.UsuarioUnidadeDAO;
import gestao.persistence.util.HibernateUtil;

public class TesteExcluirUsuarioUnidade {

    public static void main(String[] args) {

        UsuarioUnidadeDAO vinculoDAO = new UsuarioUnidadeDAO();

        try {
            vinculoDAO.excluir(1);
        } finally {
            HibernateUtil.fechar();
        }
    }
}
