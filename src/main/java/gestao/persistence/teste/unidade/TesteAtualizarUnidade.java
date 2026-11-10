
package gestao.persistence.teste.unidade;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;

public class TesteAtualizarUnidade {

    public static void main(String[] args) {

        UnidadeDAO unidadeDAO = new UnidadeDAO();

        // Busca a unidade cadastrada
        UnidadeEntity unidade = unidadeDAO.buscarPorId(4);

        if (unidade != null) {

            // Modifica as informações
            unidade.setAndar(2);
            unidade.setObservacoes("Unidade atualizada pelo DAO");

            // Salva as alterações
            unidadeDAO.atualizar(unidade);

        } else {
            System.out.println("Unidade não encontrada.");
        }

        HibernateUtil.fechar();
    }
}
