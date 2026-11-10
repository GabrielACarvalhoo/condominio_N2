
package gestao.persistence.teste.usuario;

import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.entity.Usuario;
import gestao.persistence.util.HibernateUtil;

import java.util.List;

public class TesteConsultaUsuarioDAO {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // Listar usuários
            System.out.println("=== USUÁRIOS CADASTRADOS ===");

            List<Usuario> usuarios = usuarioDAO.listarTodos();

            for (Usuario usuario : usuarios) {
                System.out.println(
                        "ID: " + usuario.getId() +
                                " | Nome: " + usuario.getNome() +
                                " | E-mail: " + usuario.getEmail()
                );
            }

            // Buscar usuário por ID
            System.out.println("\n=== BUSCA POR ID ===");

            Usuario usuario = usuarioDAO.buscarPorId(4);

            if (usuario != null) {
                System.out.println(
                        "Usuário encontrado: " +
                                usuario.getNome()
                );
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } finally {
            HibernateUtil.fechar();
        }
    }
}
