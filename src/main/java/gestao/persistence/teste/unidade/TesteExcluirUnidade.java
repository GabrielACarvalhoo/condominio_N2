
package gestao.persistence.teste.unidade;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.util.HibernateUtil;

public class TesteExcluirUnidade {

    public static void main(String[] args) {

        UnidadeDAO unidadeDAO = new UnidadeDAO();

        // Exclui a unidade de ID 1
        unidadeDAO.excluir(4);

        HibernateUtil.fechar();
    }
}
