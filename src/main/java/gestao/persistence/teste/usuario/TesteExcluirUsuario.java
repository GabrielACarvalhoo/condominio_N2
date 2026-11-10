
package gestao.persistence.teste.usuario;

import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.util.HibernateUtil;

public class TesteExcluirUsuario {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Exclui o usuário de ID 1
        usuarioDAO.excluir(4);

        HibernateUtil.fechar();
    }
}
