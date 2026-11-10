
package gestao.persistence.teste.unidade;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;

import java.math.BigDecimal;

public class TesteUnidadeDAO {

    public static void main(String[] args) {

        UnidadeDAO unidadeDAO = new UnidadeDAO();

        try {
            UnidadeEntity unidade = new UnidadeEntity();

            unidade.setBloco("A");
            unidade.setNumero("102");
            unidade.setAndar(1);
            unidade.setMetragem(new BigDecimal("65.50"));
            unidade.setObservacoes("Unidade cadastrada pelo Hibernate");

            unidadeDAO.salvar(unidade);

            System.out.println("ID gerado: " + unidade.getId());

        } finally {
            HibernateUtil.fechar();
        }
    }
}
