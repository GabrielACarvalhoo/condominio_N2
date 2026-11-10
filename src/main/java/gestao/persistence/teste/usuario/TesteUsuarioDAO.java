
package gestao.persistence.teste.usuario;

import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.entity.Perfil;
import gestao.persistence.entity.Usuario;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;

public class TesteUsuarioDAO {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            // Busca um perfil já existente no banco
            Perfil perfil = session.find(Perfil.class, 1);

            if (perfil == null) {
                System.out.println("Perfil não encontrado.");
                return;
            }

            // Cria o usuário de teste
            Usuario usuario = new Usuario(
                    perfil,
                    "Usuario Teste",
                    "12345678902",
                    "joao.teste2@condominio.com",
                    "61999999999",
                    "hash_teste_123"
            );

            // Cadastra utilizando o DAO
            usuarioDAO.salvar(usuario);

        } finally {
            HibernateUtil.fechar();
        }
    }
}
