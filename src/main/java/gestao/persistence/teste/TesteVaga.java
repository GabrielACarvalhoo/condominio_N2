
package gestao.persistence.teste;

import gestao.persistence.dao.VagaDAO;
import gestao.persistence.entity.Vaga;
import gestao.persistence.entity.TipoVaga;

public class TesteVaga {

    public static void main(String[] args) {

        VagaDAO vagaDAO = new VagaDAO();

        Vaga vaga = new Vaga();
        vaga.setIdentificacao("VAGA-TESTE-01");
        vaga.setTipo(TipoVaga.CARRO);
        vaga.setCoberta(true);

        vagaDAO.salvar(vaga);

        System.out.println("\n--- VAGAS CADASTRADAS ---");

        for (Vaga v : vagaDAO.listarTodos()) {
            System.out.println(
                    "ID: " + v.getId()
                            + " | Identificação: " + v.getIdentificacao()
                            + " | Tipo: " + v.getTipo()
                            + " | Coberta: " + v.isCoberta()
                            + " | Status: " + v.getStatus()
            );
        }

        gestao.persistence.util.HibernateUtil.fechar();
    }
}
