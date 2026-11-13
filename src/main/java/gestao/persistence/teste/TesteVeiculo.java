
package gestao.persistence.teste;

import gestao.persistence.dao.VeiculoDAO;
import gestao.persistence.dao.VagaDAO;
import gestao.persistence.entity.*;
import gestao.persistence.util.HibernateUtil;

public class TesteVeiculo {

    public static void main(String[] args) {

        VeiculoDAO veiculoDAO = new VeiculoDAO();
        VagaDAO vagaDAO = new VagaDAO();

        Veiculo veiculo = new Veiculo();

        UnidadeEntity unidade = new UnidadeEntity();
        // Unidade existente: Bloco A, apartamento 101
        // O DAO recuperará a entidade pelo ID.
        // Se sua UnidadeEntity não tiver construtor vazio público,
        // me avise.

        // Não precisamos instanciar as associações manualmente:
        // vamos buscar os registros pelo Hibernate no DAO.
        Vaga vaga = new Vaga();

        // Preencha os IDs das associações
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            unidade = session.find(UnidadeEntity.class, 2);
            vaga = session.find(Vaga.class, 1);
        }

        veiculo.setUnidade(unidade);
        veiculo.setVaga(vaga);
        veiculo.setPlaca("ABC1D23");
        veiculo.setModelo("Onix");
        veiculo.setMarca("Chevrolet");
        veiculo.setCor("Prata");
        veiculo.setTipo(TipoVeiculo.CARRO);

        veiculoDAO.salvar(veiculo);

        System.out.println("\n--- VEÍCULOS CADASTRADOS ---");

        for (Veiculo v : veiculoDAO.listarTodos()) {
            System.out.println(
                    "ID: " + v.getId()
                            + " | Placa: " + v.getPlaca()
                            + " | Modelo: " + v.getModelo()
                            + " | Unidade: " + v.getUnidade().getBloco()
                            + " - " + v.getUnidade().getNumero()
                            + " | Vaga: " + v.getVaga().getIdentificacao()
            );
        }

        HibernateUtil.fechar();
    }
}
