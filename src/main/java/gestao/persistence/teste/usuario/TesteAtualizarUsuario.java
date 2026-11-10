
package gestao.persistence.teste.usuario;

import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.entity.Usuario;
import gestao.persistence.util.HibernateUtil;

public class TesteAtualizarUsuario {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.buscarPorId(4);

        if (usuario != null) {

            usuario.setNome("Usuario Teste Atualizado");
            usuario.setTelefone("61988888888");

            usuarioDAO.atualizar(usuario);

        } else {
            System.out.println("Usuário não encontrado.");
        }

        HibernateUtil.fechar();
    }
}
