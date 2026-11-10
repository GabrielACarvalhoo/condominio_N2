
package gestao.persistence.teste.unidade;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.util.HibernateUtil;

import java.util.List;

public class TesteConsultaUnidadeDAO {

    public static void main(String[] args) {

        UnidadeDAO unidadeDAO = new UnidadeDAO();

        // Testando a listagem
        System.out.println("=== TODAS AS UNIDADES ===");

        List<UnidadeEntity> unidades = unidadeDAO.listarTodos();

        for (UnidadeEntity unidade : unidades) {
            System.out.println(
                    unidade.getId() + " - Bloco " +
                            unidade.getBloco() + " - Unidade " +
                            unidade.getNumero()
            );
        }

        // Testando a busca por ID
        System.out.println("\n=== BUSCA POR ID ===");

        UnidadeEntity unidade = unidadeDAO.buscarPorId(1);

        if (unidade != null) {
            System.out.println(
                    "Unidade encontrada: " +
                            unidade.getBloco() + " - " +
                            unidade.getNumero()
            );
        } else {
            System.out.println("Unidade não encontrada.");
        }

        HibernateUtil.fechar();
    }
}
