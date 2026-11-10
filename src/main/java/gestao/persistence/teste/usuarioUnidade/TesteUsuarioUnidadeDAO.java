
package gestao.persistence.teste.usuarioUnidade;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.dao.UsuarioUnidadeDAO;
import gestao.persistence.entity.Perfil;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.entity.Usuario;
import gestao.persistence.entity.UsuarioUnidade;
import gestao.persistence.entity.TipoVinculo;
import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteUsuarioUnidadeDAO {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        UsuarioUnidadeDAO vinculoDAO = new UsuarioUnidadeDAO();

        try {
            // 1. Buscar o perfil PROPRIETARIO
            Perfil perfil;

            try (Session session = HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

                perfil = session.find(Perfil.class, 1);
            }

            if (perfil == null) {
                System.out.println("Perfil não encontrado.");
                return;
            }

            // 2. Criar e cadastrar o usuário
            Usuario usuario = new Usuario(
                    perfil,
                    "Joao Teste",
                    "12345678901",
                    "joao.teste@condominio.com",
                    "61999999999",
                    "hash_teste_123"
            );

            usuarioDAO.salvar(usuario);

            // 3. Criar e cadastrar a unidade
            UnidadeEntity unidade = new UnidadeEntity();

            unidade.setBloco("A");
            unidade.setNumero("101");
            unidade.setAndar(1);
            unidade.setMetragem(new BigDecimal("65.50"));
            unidade.setObservacoes("Unidade de teste");

            unidadeDAO.salvar(unidade);

            // 4. Criar o vínculo entre usuário e unidade
            UsuarioUnidade vinculo = new UsuarioUnidade(
                    usuario,
                    unidade,
                    TipoVinculo.PROPRIETARIO,
                    LocalDate.now(),
                    true
            );

            vinculoDAO.salvar(vinculo);

            System.out.println("Teste de vínculo finalizado!");

        } finally {
            HibernateUtil.fechar();
        }
    }
}
