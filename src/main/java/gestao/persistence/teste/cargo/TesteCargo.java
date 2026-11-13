
package gestao.teste;

import gestao.persistence.dao.CargoDAO;
import gestao.persistence.entity.Cargo;
import gestao.persistence.util.HibernateUtil;

public class TesteCargo {

    public static void main(String[] args) {

        CargoDAO cargoDAO = new CargoDAO();

        try {
            // 1. CADASTRAR
            System.out.println("\n=== 1. CADASTRAR CARGO ===");

            Cargo cargo = new Cargo(
                    "Cargo de Teste",
                    "Cargo criado para testar o CRUD"
            );

            cargoDAO.salvar(cargo);

            Integer idCargo = cargo.getId();

            System.out.println("ID gerado: " + idCargo);

            // 2. CONSULTAR POR ID
            System.out.println("\n=== 2. CONSULTAR CARGO ===");

            Cargo cargoEncontrado = cargoDAO.buscarPorId(idCargo);

            if (cargoEncontrado != null) {
                System.out.println("ID: " + cargoEncontrado.getId());
                System.out.println("Nome: " + cargoEncontrado.getNome());
                System.out.println("Descrição: " + cargoEncontrado.getDescricao());
            }

            // 3. ATUALIZAR
            System.out.println("\n=== 3. ATUALIZAR CARGO ===");

            cargoEncontrado.setDescricao(
                    "Descrição atualizada durante o teste"
            );

            cargoDAO.atualizar(cargoEncontrado);

            // 4. CONSULTAR NOVAMENTE
            System.out.println("\n=== 4. CONFERIR ATUALIZAÇÃO ===");

            Cargo cargoAtualizado = cargoDAO.buscarPorId(idCargo);

            System.out.println("Nome: " + cargoAtualizado.getNome());
            System.out.println("Nova descrição: " + cargoAtualizado.getDescricao());

            // 5. EXCLUIR
            System.out.println("\n=== 5. EXCLUIR CARGO ===");

            cargoDAO.excluir(idCargo);

            // 6. CONFIRMAR EXCLUSÃO
            System.out.println("\n=== 6. CONFIRMAR EXCLUSÃO ===");

            Cargo cargoExcluido = cargoDAO.buscarPorId(idCargo);

            if (cargoExcluido == null) {
                System.out.println("Cargo removido com sucesso!");
            } else {
                System.out.println("ERRO: o cargo ainda existe!");
            }

        } catch (Exception e) {
            System.out.println("Erro durante o teste:");
            e.printStackTrace();

        } finally {
            HibernateUtil.fechar();
        }
    }
}
