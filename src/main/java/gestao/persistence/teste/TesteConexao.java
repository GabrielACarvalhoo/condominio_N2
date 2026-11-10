
package gestao.persistence.teste;

import gestao.persistence.util.HibernateUtil;
import org.hibernate.Session;

public class TesteConexao {

    public static void main(String[] args) {

        try {
            System.out.println("Inicializando Hibernate...");

            try (Session session = HibernateUtil
                    .getSessionFactory()
                    .openSession()) {

                session.doWork(connection -> {
                    System.out.println("Conexão estabelecida!");
                    System.out.println(
                            "Banco conectado: " + connection.getCatalog()
                    );
                    System.out.println(
                            "MySQL: " +
                                    connection.getMetaData().getDatabaseProductVersion()
                    );
                });
            }

        } catch (Exception e) {
            System.err.println("Falha na conexão com o banco:");
            e.printStackTrace();

        } finally {
            HibernateUtil.fechar();
        }
    }
}
