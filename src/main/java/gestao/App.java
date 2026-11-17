package gestao;

import java.util.Scanner;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.hibernate.Session;

import gestao.persistence.dao.UnidadeDAO;
import gestao.persistence.dao.UsuarioDAO;
import gestao.persistence.dao.UsuarioUnidadeDAO;
import gestao.persistence.entity.UnidadeEntity;
import gestao.persistence.entity.Usuario;
import gestao.persistence.entity.Perfil;
import gestao.persistence.entity.UsuarioUnidade;
import gestao.persistence.entity.TipoVinculo;
import gestao.persistence.util.HibernateUtil;
import gestao.persistence.dao.OcorrenciaDAO;
import gestao.persistence.entity.Ocorrencia;
import gestao.persistence.entity.CategoriaOcorrencia;
import gestao.persistence.entity.StatusOcorrencia;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gestao.persistence.dao.VisitanteDAO;
import gestao.persistence.dao.AutorizacaoVisitanteDAO;

import gestao.persistence.entity.Visitante;
import gestao.persistence.entity.AutorizacaoVisitante;
import gestao.persistence.entity.StatusAutorizacao;

import gestao.persistence.dao.CargoDAO;
import gestao.persistence.entity.Cargo;
import gestao.persistence.dao.FuncionarioDAO;
import gestao.persistence.entity.Funcionario;
import gestao.persistence.dao.RegistroAcessoDAO;
import gestao.persistence.entity.RegistroAcesso;
import gestao.persistence.dao.EspacoComumDAO;
import gestao.persistence.entity.EspacoComum;
import gestao.persistence.entity.Vaga;
import gestao.persistence.entity.Veiculo;
import gestao.persistence.entity.TipoVaga;
import gestao.persistence.entity.TipoVeiculo;
import gestao.persistence.entity.StatusVaga;

import gestao.persistence.dao.ReservaDAO;
import gestao.persistence.entity.Reserva;
import gestao.persistence.entity.StatusReserva;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import gestao.persistence.dao.EncomendaDAO;
import gestao.persistence.entity.Encomenda;
import gestao.persistence.dao.VagaDAO;
import gestao.persistence.dao.VeiculoDAO;
import gestao.persistence.dao.SolicitacaoManutencaoDAO;
import gestao.persistence.entity.SolicitacaoManutencao;
import gestao.persistence.entity.PrioridadeManutencao;
import gestao.persistence.entity.StatusSolicitacao;
import gestao.persistence.dao.TaxaCondominialDAO;
import gestao.persistence.dao.PagamentoDAO;

import gestao.persistence.entity.TaxaCondominial;
import gestao.persistence.entity.Pagamento;
import gestao.persistence.entity.StatusTaxaCondominial;
import gestao.persistence.entity.FormaPagamento;

public class App {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Scanner leitura = new Scanner(System.in);

        UnidadeDAO unidadeDAO = new UnidadeDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioUnidadeDAO usuarioUnidadeDAO = new UsuarioUnidadeDAO();
        OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        AutorizacaoVisitanteDAO autorizacaoVisitanteDAO = new AutorizacaoVisitanteDAO();
        CargoDAO cargoDAO = new CargoDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        RegistroAcessoDAO registroAcessoDAO = new RegistroAcessoDAO();
        EspacoComumDAO espacoComumDAO = new EspacoComumDAO();
        ReservaDAO reservaDAO = new ReservaDAO();
        EncomendaDAO encomendaDAO = new EncomendaDAO();
        VagaDAO vagaDAO = new VagaDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        SolicitacaoManutencaoDAO solicitacaoManutencaoDAO =
                new SolicitacaoManutencaoDAO();
        TaxaCondominialDAO taxaCondominialDAO = new TaxaCondominialDAO();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        boolean executando = true;

        try {

            while (executando) {

                System.out.println("\n========== GESTÃO CONDOMINIAL ==========");
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("1 - Cadastros");
                System.out.println("2 - Visitantes, Autorizações e Acessos");
                System.out.println("3 - Ocorrências");
                System.out.println("4 - Lazer e Reservas");
                System.out.println("5 - Gestão de Encomendas");
                System.out.println("6 - Gestão de Vagas e Veículos");
                System.out.println("7 - Gestão de Manutenção");
                System.out.println("8 - Gestão Financeira");
                System.out.println("0 - Sair do sistema");


                System.out.print("\nEscolha uma opção: ");
                int opcao = lerOpcao(leitura);

                switch (opcao) {
                    case 1:
                        menuCadastros(leitura, unidadeDAO, usuarioDAO,
                                usuarioUnidadeDAO, cargoDAO, funcionarioDAO);
                        break;
                    case 2:
                        menuVisitantesAutorizacoesAcessos(
                                leitura,
                                visitanteDAO,
                                unidadeDAO,
                                usuarioDAO,
                                autorizacaoVisitanteDAO,
                                registroAcessoDAO,
                                funcionarioDAO
                        );
                        break;
                    case 3:
                        menuOcorrencias(leitura, unidadeDAO, usuarioDAO, ocorrenciaDAO);
                        break;
                    case 4:
                        menuEspacos(leitura, espacoComumDAO, reservaDAO, usuarioDAO);
                        break;
                    case 5:
                        menuEncomendas(leitura, encomendaDAO, unidadeDAO, funcionarioDAO);
                        break;
                    case 6:
                        menuVagasVeiculos(leitura, vagaDAO, veiculoDAO, unidadeDAO);
                        break;
                    case 7:
                        menuSolicitacoesManutencao(
                                leitura,
                                solicitacaoManutencaoDAO,
                                usuarioDAO,
                                unidadeDAO,
                                funcionarioDAO
                        );
                        break;
                    case 8:
                        menuFinanceiro(
                                leitura,
                                taxaCondominialDAO,
                                pagamentoDAO,
                                unidadeDAO
                        );
                        break;
                    case 0:
                        System.out.println("\nEncerrando o sistema...");
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

        } finally {

            leitura.close();
            HibernateUtil.fechar();

            System.out.println("Sistema encerrado.");
        }
    }

    // ==========================================
    // MENU DE VISITANTES, AUTORIZAÇÕES E ACESSOS
    // ==========================================

    private static void menuVisitantesAutorizacoesAcessos(
            Scanner leitura,
            VisitanteDAO visitanteDAO,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            AutorizacaoVisitanteDAO autorizacaoVisitanteDAO,
            RegistroAcessoDAO registroAcessoDAO,
            FuncionarioDAO funcionarioDAO) {

        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n========== VISITANTES E ACESSOS ==========");
            System.out.println("1 - Gestão de Visitantes");
            System.out.println("2 - Gestão de Autorizações");
            System.out.println("3 - Controle de Acesso");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {
                case 1:
                    menuVisitantes(leitura, visitanteDAO);
                    break;

                case 2:
                    menuAutorizacoes(
                            leitura,
                            visitanteDAO,
                            unidadeDAO,
                            usuarioDAO,
                            autorizacaoVisitanteDAO
                    );
                    break;

                case 3:
                    menuRegistrosAcesso(
                            leitura,
                            registroAcessoDAO,
                            autorizacaoVisitanteDAO,
                            funcionarioDAO
                    );
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
    // MENU DE CADASTROS
    // ==========================================

    private static void menuCadastros(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            UsuarioUnidadeDAO usuarioUnidadeDAO,
            CargoDAO cargoDAO,
            FuncionarioDAO funcionarioDAO) {

        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n========== CADASTROS ==========");
            System.out.println("1 - Gestão de Usuários");
            System.out.println("2 - Gestão de Unidades");
            System.out.println("3 - Gestão de Vínculos");
            System.out.println("4 - Gestão de Cargos");
            System.out.println("5 - Gestão de Funcionários");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {
                case 1:
                    menuUsuarios(leitura, usuarioDAO);
                    break;
                case 2:
                    menuUnidades(leitura, unidadeDAO);
                    break;
                case 3:
                    menuVinculos(leitura, unidadeDAO, usuarioDAO, usuarioUnidadeDAO);
                    break;
                case 4:
                    menuCargos(leitura, cargoDAO);
                    break;
                case 5:
                    menuFuncionarios(leitura, funcionarioDAO, usuarioDAO, cargoDAO);
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
    // MENU DE GESTÃO DE CARGOS
    // ==========================================

    private static void menuCargos(
            Scanner leitura,
            CargoDAO cargoDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE CARGOS =====");
            System.out.println("1 - Cadastrar cargo");
            System.out.println("2 - Listar cargos");
            System.out.println("3 - Consultar cargo por ID");
            System.out.println("4 - Atualizar cargo");
            System.out.println("5 - Excluir cargo");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarCargo(leitura, cargoDAO);
                    break;

                case 2:
                    listarCargos(cargoDAO);
                    break;

                case 3:
                    consultarCargo(leitura, cargoDAO);
                    break;

                case 4:
                    atualizarCargo(leitura, cargoDAO);
                    break;

                case 5:
                    excluirCargo(leitura, cargoDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
    // CADASTRAR CARGO
    // ==========================================

    private static void cadastrarCargo(
            Scanner leitura,
            CargoDAO cargoDAO) {

        System.out.println("\n--- CADASTRAR CARGO ---");

        try {

            System.out.print("Nome do cargo: ");
            String nome = leitura.nextLine();

            System.out.print("Descrição: ");
            String descricao = leitura.nextLine();

            if (nome.isBlank()) {
                System.out.println("O nome do cargo é obrigatório!");
                return;
            }

            Cargo cargo = new Cargo(
                    nome,
                    descricao.isBlank() ? null : descricao
            );

            cargoDAO.salvar(cargo);

        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar cargo: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR CARGOS
    // ==========================================

    private static void listarCargos(CargoDAO cargoDAO) {

        System.out.println("\n--- CARGOS CADASTRADOS ---");

        try {

            List<Cargo> cargos = cargoDAO.listarTodos();

            if (cargos.isEmpty()) {
                System.out.println("Nenhum cargo cadastrado.");
                return;
            }

            for (Cargo cargo : cargos) {

                System.out.println("------------------------------");
                System.out.println("ID: " + cargo.getId());
                System.out.println("Nome: " + cargo.getNome());
                System.out.println("Descrição: " + cargo.getDescricao());
            }

            System.out.println("------------------------------");
            System.out.println("Total de cargos: " + cargos.size());

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar cargos: " + e.getMessage());
        }
    }

    // ==========================================
    // CONSULTAR CARGO POR ID
    // ==========================================

    private static void consultarCargo(
            Scanner leitura,
            CargoDAO cargoDAO) {

        try {

            System.out.print("\nDigite o ID do cargo: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Cargo cargo = cargoDAO.buscarPorId(id);

            if (cargo == null) {
                System.out.println("Cargo não encontrado!");
                return;
            }

            System.out.println("\n--- DETALHES DO CARGO ---");
            System.out.println("ID: " + cargo.getId());
            System.out.println("Nome: " + cargo.getNome());
            System.out.println("Descrição: " + cargo.getDescricao());

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println("Erro ao consultar cargo: " + e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAR CARGO
    // ==========================================

    private static void atualizarCargo(
            Scanner leitura,
            CargoDAO cargoDAO) {

        System.out.println("\n--- ATUALIZAR CARGO ---");

        listarCargos(cargoDAO);

        try {

            System.out.print("\nDigite o ID do cargo: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Cargo cargo = cargoDAO.buscarPorId(id);

            if (cargo == null) {
                System.out.println("Cargo não encontrado!");
                return;
            }

            System.out.println("\nCargo selecionado: " + cargo.getNome());

            System.out.print("Novo nome: ");
            String nome = leitura.nextLine();

            if (nome.isBlank()) {
                System.out.println("O nome do cargo é obrigatório!");
                return;
            }

            System.out.print("Nova descrição: ");
            String descricao = leitura.nextLine();

            cargo.setNome(nome);
            cargo.setDescricao(descricao.isBlank() ? null : descricao);

            cargoDAO.atualizar(cargo);

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar cargo: " + e.getMessage());
        }
    }

    // ==========================================
    // EXCLUIR CARGO
    // ==========================================

    private static void excluirCargo(
            Scanner leitura,
            CargoDAO cargoDAO) {

        System.out.println("\n--- EXCLUIR CARGO ---");

        listarCargos(cargoDAO);

        try {

            System.out.print("\nDigite o ID do cargo: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Cargo cargo = cargoDAO.buscarPorId(id);

            if (cargo == null) {
                System.out.println("Cargo não encontrado!");
                return;
            }

            System.out.println("\nCargo selecionado:");
            System.out.println("Nome: " + cargo.getNome());
            System.out.println("Descrição: " + cargo.getDescricao());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                cargoDAO.excluir(id);
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir cargo: " + e.getMessage());
            System.out.println(
                    "Verifique se existem funcionários vinculados a este cargo."
            );
        }
    }
    private static String formatarData(LocalDate data) {
        if (data == null) {
            return "Não informada";
        }

        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // ==========================================
    // MENU DE GESTÃO DE FUNCIONÁRIOS
    // ==========================================

    private static void menuFuncionarios(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO,
            UsuarioDAO usuarioDAO,
            CargoDAO cargoDAO) {

        boolean voltar = false;

        while (!voltar) {


            System.out.println("\n===== GESTÃO DE FUNCIONÁRIOS =====");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Listar funcionários");
            System.out.println("3 - Consultar funcionário por ID");
            System.out.println("4 - Atualizar funcionário");
            System.out.println("5 - Registrar demissão");
            System.out.println("6 - Excluir funcionário");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarFuncionario(
                            leitura, funcionarioDAO, usuarioDAO, cargoDAO);
                    break;

                case 2:
                    listarFuncionarios(funcionarioDAO);
                    break;

                case 3:
                    consultarFuncionario(leitura, funcionarioDAO);
                    break;

                case 4:
                    atualizarFuncionario(
                            leitura, funcionarioDAO, usuarioDAO, cargoDAO);
                    break;

                case 5:
                    registrarDemissao(leitura, funcionarioDAO);
                    break;

                case 6:
                    excluirFuncionario(leitura, funcionarioDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
    // VERIFICAR SE USUÁRIO JÁ É FUNCIONÁRIO
    // ==========================================

    private static boolean usuarioJaPossuiFuncionario(
            Integer idUsuario,
            Integer idFuncionarioAtual,
            FuncionarioDAO funcionarioDAO) {

        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

        for (Funcionario funcionario : funcionarios) {

            boolean mesmoUsuario =
                    funcionario.getUsuario().getId().equals(idUsuario);

            boolean outroFuncionario =
                    !funcionario.getId().equals(idFuncionarioAtual);

            if (mesmoUsuario && outroFuncionario) {
                return true;
            }
        }

        return false;
    }

    // ==========================================
    // CADASTRAR FUNCIONÁRIO
    // ==========================================

    private static void cadastrarFuncionario(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO,
            UsuarioDAO usuarioDAO,
            CargoDAO cargoDAO) {

        System.out.println("\n--- CADASTRAR FUNCIONÁRIO ---");

        try {

            System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
            listarUsuarios(usuarioDAO);

            System.out.print("\nDigite o ID do usuário: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            if (usuarioJaPossuiFuncionario(
                    idUsuario, null, funcionarioDAO)) {
                System.out.println(
                        "Este usuário já está cadastrado como funcionário!");
                return;
            }

            System.out.println("\n--- CARGOS DISPONÍVEIS ---");
            listarCargos(cargoDAO);

            System.out.print("\nDigite o ID do cargo: ");
            Integer idCargo = Integer.parseInt(leitura.nextLine());

            Cargo cargo = cargoDAO.buscarPorId(idCargo);

            if (cargo == null) {
                System.out.println("Cargo não encontrado!");
                return;
            }

            System.out.print("Data de admissão (dd/MM/yyyy): ");
            String dataAdmissaoTexto = leitura.nextLine();

            LocalDate dataAdmissao = dataAdmissaoTexto.isBlank()
                    ? null
                    : LocalDate.parse(
                    dataAdmissaoTexto,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            Funcionario funcionario = new Funcionario(
                    usuario,
                    cargo,
                    dataAdmissao,
                    null
            );

            funcionarioDAO.salvar(funcionario);

            System.out.println("\nFuncionário cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe IDs numéricos válidos.");

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Data inválida! Utilize dd/MM/yyyy.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR FUNCIONÁRIOS
    // ==========================================

    private static void listarFuncionarios(
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- FUNCIONÁRIOS CADASTRADOS ---");

        try {

            List<Funcionario> funcionarios =
                    funcionarioDAO.listarTodos();

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
                return;
            }

            for (Funcionario funcionario : funcionarios) {

                System.out.println("------------------------------");
                System.out.println("ID: " + funcionario.getId());
                System.out.println(
                        "Usuário: " + funcionario.getUsuario().getNome());
                System.out.println(
                        "Cargo: " + funcionario.getCargo().getNome());
                System.out.println(
                        "Admissão: " + formatarData(funcionario.getDataAdmissao()));

                System.out.println(
                        "Demissão: " + formatarData(funcionario.getDataDemissao()));
            }

            System.out.println("------------------------------");
            System.out.println(
                    "Total de funcionários: " + funcionarios.size());

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao listar funcionários: " + e.getMessage());
        }
    }

    // ==========================================
    // CONSULTAR FUNCIONÁRIO POR ID
    // ==========================================

    private static void consultarFuncionario(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO) {

        try {

            System.out.print("\nDigite o ID do funcionário: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado!");
                return;
            }

            System.out.println("\n--- DETALHES DO FUNCIONÁRIO ---");
            System.out.println("ID: " + funcionario.getId());
            System.out.println(
                    "Usuário: " + funcionario.getUsuario().getNome());
            System.out.println(
                    "Cargo: " + funcionario.getCargo().getNome());
            System.out.println(
                    "Data de admissão: " + formatarData(funcionario.getDataAdmissao()));

            System.out.println(
                    "Data de demissão: " + formatarData(funcionario.getDataDemissao()));

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao consultar funcionário: " + e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAR FUNCIONÁRIO
    // ==========================================


    private static void atualizarFuncionario(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO,
            UsuarioDAO usuarioDAO,
            CargoDAO cargoDAO) {

        System.out.println("\n--- ATUALIZAR FUNCIONÁRIO ---");

        listarFuncionarios(funcionarioDAO);

        try {

            System.out.print("\nDigite o ID do funcionário: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado!");
                return;
            }

            System.out.println("\nFuncionário selecionado:");
            System.out.println("Usuário: " + funcionario.getUsuario().getNome());
            System.out.println("Cargo: " + funcionario.getCargo().getNome());

            System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
            listarUsuarios(usuarioDAO);

            System.out.print("\nNovo ID do usuário: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            if (usuarioJaPossuiFuncionario(
                    idUsuario, id, funcionarioDAO)) {
                System.out.println(
                        "Este usuário já está vinculado a outro funcionário!");
                return;
            }

            System.out.println("\n--- CARGOS DISPONÍVEIS ---");
            listarCargos(cargoDAO);

            System.out.print("\nNovo ID do cargo: ");
            Integer idCargo = Integer.parseInt(leitura.nextLine());

            Cargo cargo = cargoDAO.buscarPorId(idCargo);

            if (cargo == null) {
                System.out.println("Cargo não encontrado!");
                return;
            }

            funcionario.setUsuario(usuario);
            funcionario.setCargo(cargo);

            funcionarioDAO.atualizar(funcionario);

            System.out.println("\nFuncionário atualizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe IDs numéricos válidos.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao atualizar funcionário: " + e.getMessage());
        }
    }



    private static void registrarDemissao(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- REGISTRAR DEMISSÃO ---");

        listarFuncionarios(funcionarioDAO);

        try {

            System.out.print("\nDigite o ID do funcionário: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado!");
                return;
            }

            System.out.println("\nFuncionário selecionado:");
            System.out.println("Usuário: " + funcionario.getUsuario().getNome());
            System.out.println("Cargo: " + funcionario.getCargo().getNome());
            System.out.println(
                    "Admissão: " + formatarData(funcionario.getDataAdmissao()));

            System.out.println(
                    "Demissão: " + formatarData(funcionario.getDataDemissao()));

            if (funcionario.getDataDemissao() != null) {
                System.out.println(
                        "\nEste funcionário já possui uma data de demissão!");
                return;
            }

            System.out.print(
                    "\nData de demissão (dd/MM/yyyy): ");

            String dataTexto = leitura.nextLine();

            LocalDate dataDemissao = LocalDate.parse(
                    dataTexto,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            if (funcionario.getDataAdmissao() != null
                    && dataDemissao.isBefore(funcionario.getDataAdmissao())) {
                System.out.println(
                        "A data de demissão não pode ser anterior à admissão!");
                return;
            }

            funcionario.setDataDemissao(dataDemissao);

            funcionarioDAO.atualizar(funcionario);

            System.out.println(
                    "\nDemissão registrada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Data inválida! Utilize dd/MM/yyyy.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao registrar demissão: " + e.getMessage());
        }
    }


    // ==========================================
    // EXCLUIR FUNCIONÁRIO
    // ==========================================

    private static void excluirFuncionario(
            Scanner leitura,
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- EXCLUIR FUNCIONÁRIO ---");

        listarFuncionarios(funcionarioDAO);

        try {

            System.out.print("\nDigite o ID do funcionário: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado!");
                return;
            }

            System.out.println("\nFuncionário selecionado:");
            System.out.println(
                    "Usuário: " + funcionario.getUsuario().getNome());
            System.out.println(
                    "Cargo: " + funcionario.getCargo().getNome());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                funcionarioDAO.excluir(id);
                System.out.println(
                        "Funcionário excluído com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao excluir funcionário: " + e.getMessage());
        }
    }



    // ==========================================
    // MENU DE MORADORES E UNIDADES
    // ==========================================


    private static void menuUnidades(
            Scanner leitura,
            UnidadeDAO unidadeDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE UNIDADES =====");
            System.out.println("1 - Cadastrar unidade");
            System.out.println("2 - Listar unidades");
            System.out.println("3 - Atualizar unidade");
            System.out.println("4 - Excluir unidade");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarUnidade(leitura, unidadeDAO);
                    break;

                case 2:
                    listarUnidades(unidadeDAO);
                    break;

                case 3:
                    atualizarUnidade(leitura, unidadeDAO);
                    break;

                case 4:
                    excluirUnidade(leitura, unidadeDAO);
                    break;
                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private static void menuUsuarios(
            Scanner leitura,
            UsuarioDAO usuarioDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE USUÁRIOS =====");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarUsuario(leitura, usuarioDAO);
                    break;

                case 2:
                    listarUsuarios(usuarioDAO);
                    break;

                case 3:
                    atualizarUsuario(leitura, usuarioDAO);
                    break;

                case 4:
                    excluirUsuario(leitura, usuarioDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuVinculos(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            UsuarioUnidadeDAO usuarioUnidadeDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE VÍNCULOS =====");
            System.out.println("1 - Cadastrar vínculo");
            System.out.println("2 - Listar vínculos");
            System.out.println("3 - Atualizar vínculo");
            System.out.println("4 - Excluir vínculo");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
                    listarUsuarios(usuarioDAO);

                    System.out.println("\n--- UNIDADES DISPONÍVEIS ---");
                    listarUnidades(unidadeDAO);

                    vincularUsuarioUnidade(
                            leitura,
                            unidadeDAO,
                            usuarioDAO,
                            usuarioUnidadeDAO
                    );
                    break;

                case 2:
                    listarVinculos(usuarioUnidadeDAO);
                    break;

                case 3:
                    atualizarVinculo(
                            leitura,
                            unidadeDAO,
                            usuarioDAO,
                            usuarioUnidadeDAO
                    );
                    break;

                case 4:
                    excluirVinculo(leitura, usuarioUnidadeDAO);
                    break;
                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private static void menuOcorrencias(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            OcorrenciaDAO ocorrenciaDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE OCORRÊNCIAS =====");
            System.out.println("1 - Cadastrar ocorrência");
            System.out.println("2 - Listar ocorrências");
            System.out.println("3 - Consultar ocorrência por ID");
            System.out.println("4 - Atualizar ocorrência");
            System.out.println("5 - Excluir ocorrência");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarOcorrencia(
                            leitura, unidadeDAO, usuarioDAO, ocorrenciaDAO);
                    break;

                case 2:
                    listarOcorrencias(ocorrenciaDAO);
                    break;

                case 3:
                    consultarOcorrencia(leitura, ocorrenciaDAO);
                    break;

                case 4:
                    atualizarOcorrencia(
                            leitura, unidadeDAO, ocorrenciaDAO);
                    break;

                case 5:
                    excluirOcorrencia(leitura, ocorrenciaDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }




    // ==========================================
    // CADASTRAR UNIDADE
    // ==========================================

    private static void cadastrarUnidade(
            Scanner leitura,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- CADASTRAR UNIDADE ---");

        try {

            System.out.print("Digite o bloco: ");
            String bloco = leitura.nextLine();

            System.out.print("Digite o número da unidade: ");
            String numero = leitura.nextLine();

            System.out.print("Digite o andar: ");
            Integer andar = Integer.parseInt(leitura.nextLine());

            System.out.print("Digite a metragem: ");
            BigDecimal metragem = new BigDecimal(
                    leitura.nextLine().replace(",", ".")
            );

            System.out.print("Digite as observações: ");
            String observacoes = leitura.nextLine();

            UnidadeEntity unidade = new UnidadeEntity();

            unidade.setBloco(bloco);
            unidade.setNumero(numero);
            unidade.setAndar(andar);
            unidade.setMetragem(metragem);
            unidade.setObservacoes(observacoes);

            unidadeDAO.salvar(unidade);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe valores numéricos válidos.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar unidade: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR UNIDADES
    // ==========================================

    private static void listarUnidades(UnidadeDAO unidadeDAO) {

        System.out.println("\n--- UNIDADES CADASTRADAS ---");

        try {

            List<UnidadeEntity> unidades = unidadeDAO.listarTodos();

            if (unidades.isEmpty()) {

                System.out.println("Nenhuma unidade cadastrada.");

            } else {

                for (UnidadeEntity unidade : unidades) {

                    System.out.println("------------------------------");
                    System.out.println("ID: " + unidade.getId());
                    System.out.println("Bloco: " + unidade.getBloco());
                    System.out.println("Número: " + unidade.getNumero());
                    System.out.println("Andar: " + unidade.getAndar());
                    System.out.println("Metragem: " + unidade.getMetragem());
                    System.out.println("Observações: " + unidade.getObservacoes());
                }

                System.out.println("------------------------------");
                System.out.println("Total de unidades: " + unidades.size());
            }

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar unidades: " + e.getMessage());
        }
    }


    // ==========================================
    // ATUALIZAR UNIDADE
    // ==========================================

    private static void atualizarUnidade(
            Scanner leitura,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- ATUALIZAR UNIDADE ---");

        // Primeiro, exibe as unidades cadastradas
        listarUnidades(unidadeDAO);

        try {

            System.out.print("\nDigite o ID da unidade que deseja atualizar: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca a unidade pelo ID
            UnidadeEntity unidade = unidadeDAO.buscarPorId(id);

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            System.out.println("\nInforme os novos dados:");

            System.out.print("Novo bloco: ");
            unidade.setBloco(leitura.nextLine());

            System.out.print("Novo número: ");
            unidade.setNumero(leitura.nextLine());

            System.out.print("Novo andar: ");
            unidade.setAndar(Integer.parseInt(leitura.nextLine()));

            System.out.print("Nova metragem: ");
            unidade.setMetragem(
                    new BigDecimal(leitura.nextLine().replace(",", "."))
            );

            System.out.print("Novas observações: ");
            unidade.setObservacoes(leitura.nextLine());

            // Salva as alterações no banco
            unidadeDAO.atualizar(unidade);

            System.out.println("\nUnidade atualizada com sucesso!");

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe valores numéricos válidos.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar unidade: " + e.getMessage());
        }
    }

    // ==========================================
    // EXCLUIR UNIDADE
    // ==========================================

    private static void excluirUnidade(
            Scanner leitura,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- EXCLUIR UNIDADE ---");

        // Exibe as unidades cadastradas
        listarUnidades(unidadeDAO);

        try {

            System.out.print("\nDigite o ID da unidade que deseja excluir: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca a unidade pelo ID
            UnidadeEntity unidade = unidadeDAO.buscarPorId(id);

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            // Exibe os dados da unidade selecionada
            System.out.println("\nUnidade selecionada:");
            System.out.println("Bloco: " + unidade.getBloco());
            System.out.println("Número: " + unidade.getNumero());

            // Solicita confirmação
            System.out.print("\nTem certeza que deseja excluir? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                unidadeDAO.excluir(id);

            } else {

                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir unidade: " + e.getMessage());
            System.out.println(
                    "Verifique se existem vínculos associados a esta unidade."
            );
        }
    }


    // ==========================================
    // CADASTRAR USUÁRIO
    // ==========================================

    private static void cadastrarUsuario(
            Scanner leitura,
            UsuarioDAO usuarioDAO) {

        System.out.println("\n--- CADASTRAR USUÁRIO ---");

        try {

            System.out.print("Digite o nome: ");
            String nome = leitura.nextLine();

            System.out.print("Digite o CPF: ");
            String cpf = leitura.nextLine();

            System.out.print("Digite o e-mail: ");
            String email = leitura.nextLine();

            System.out.print("Digite o telefone: ");
            String telefone = leitura.nextLine();

            System.out.println("\n--- PERFIS DISPONÍVEIS ---");

            try (Session session = HibernateUtil
                    .getSessionFactory().openSession()) {

                List<Perfil> perfis = session
                        .createQuery("FROM Perfil", Perfil.class)
                        .list();

                for (Perfil perfil : perfis) {

                    System.out.println(
                            perfil.getId() + " - " + perfil.getNome()
                    );
                }

                System.out.print("\nEscolha o ID do perfil: ");

                int idPerfil = Integer.parseInt(leitura.nextLine());

                Perfil perfilEscolhido = session.get(
                        Perfil.class,
                        idPerfil
                );

                if (perfilEscolhido == null) {

                    System.out.println("Perfil não encontrado!");

                } else {

                    Usuario usuario = new Usuario(
                            perfilEscolhido,
                            nome,
                            cpf,
                            email,
                            telefone,
                            "hash_demo"
                    );

                    usuarioDAO.salvar(usuario);
                }
            }

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID de perfil válido.");

        } catch (Exception e) {

            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR USUÁRIOS
    // ==========================================

    private static void listarUsuarios(UsuarioDAO usuarioDAO) {

        System.out.println("\n--- USUÁRIOS CADASTRADOS ---");

        try {

            List<Usuario> usuarios = usuarioDAO.listarTodos();

            if (usuarios.isEmpty()) {

                System.out.println("Nenhum usuário cadastrado.");

            } else {

                for (Usuario usuario : usuarios) {

                    System.out.println("------------------------------");
                    System.out.println("ID: " + usuario.getId());
                    System.out.println("Nome: " + usuario.getNome());
                    System.out.println("CPF: " + usuario.getCpf());
                    System.out.println("E-mail: " + usuario.getEmail());
                    System.out.println("Telefone: " + usuario.getTelefone());
                    System.out.println("Perfil: " + usuario.getPerfil().getNome());
                    System.out.println("Ativo: " + usuario.getAtivo());
                }

                System.out.println("------------------------------");
                System.out.println("Total de usuários: " + usuarios.size());
            }

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar usuários: " + e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAR USUÁRIO
    // ==========================================

    private static void atualizarUsuario(
            Scanner leitura,
            UsuarioDAO usuarioDAO) {

        System.out.println("\n--- ATUALIZAR USUÁRIO ---");

        // Exibe os usuários cadastrados
        listarUsuarios(usuarioDAO);

        try {

            System.out.print("\nDigite o ID do usuário que deseja atualizar: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca o usuário pelo ID
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            System.out.println("\nUsuário selecionado: " + usuario.getNome());
            System.out.println("\nInforme os novos dados:");

            System.out.print("Novo nome: ");
            usuario.setNome(leitura.nextLine());

            System.out.print("Novo CPF: ");
            usuario.setCpf(leitura.nextLine());

            System.out.print("Novo e-mail: ");
            usuario.setEmail(leitura.nextLine());

            System.out.print("Novo telefone: ");
            usuario.setTelefone(leitura.nextLine());

            // Salva as alterações no banco
            usuarioDAO.atualizar(usuario);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // ==========================================
    // EXCLUIR USUÁRIO
    // ==========================================

    private static void excluirUsuario(
            Scanner leitura,
            UsuarioDAO usuarioDAO) {

        System.out.println("\n--- EXCLUIR USUÁRIO ---");

        // Exibe os usuários cadastrados
        listarUsuarios(usuarioDAO);

        try {

            System.out.print("\nDigite o ID do usuário que deseja excluir: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca o usuário pelo ID
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            // Exibe os dados do usuário selecionado
            System.out.println("\nUsuário selecionado:");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("E-mail: " + usuario.getEmail());

            // Solicita confirmação
            System.out.print("\nTem certeza que deseja excluir? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                usuarioDAO.excluir(id);

            } else {

                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            System.out.println(
                    "Verifique se existem vínculos associados a este usuário."
            );
        }
    }


    // ==========================================
    // VINCULAR USUÁRIO A UMA UNIDADE
    // ==========================================

    private static void vincularUsuarioUnidade(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            UsuarioUnidadeDAO usuarioUnidadeDAO) {

        System.out.println("\n--- VINCULAR USUÁRIO A UMA UNIDADE ---");

        try {

            System.out.print("Digite o ID do usuário: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            System.out.print("Digite o ID da unidade: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            System.out.println("\nTipos de vínculo disponíveis:");
            System.out.println("1 - PROPRIETARIO");
            System.out.println("2 - INQUILINO");
            System.out.println("3 - MORADOR");

            System.out.print("Escolha o tipo de vínculo: ");

            int tipoEscolhido = Integer.parseInt(leitura.nextLine());

            System.out.print("É responsável financeiro? (S/N): ");

            boolean responsavelFinanceiro =
                    leitura.nextLine().equalsIgnoreCase("S");

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (usuario == null || unidade == null) {

                System.out.println("Usuário ou unidade não encontrado!");
                return;
            }

            TipoVinculo tipoVinculo;

            switch (tipoEscolhido) {

                case 1:
                    tipoVinculo = TipoVinculo.PROPRIETARIO;
                    break;

                case 2:
                    tipoVinculo = TipoVinculo.INQUILINO;
                    break;

                case 3:
                    tipoVinculo = TipoVinculo.MORADOR;
                    break;

                default:
                    System.out.println("Tipo de vínculo inválido!");
                    return;
            }

            UsuarioUnidade vinculo = new UsuarioUnidade(
                    usuario,
                    unidade,
                    tipoVinculo,
                    LocalDate.now(),
                    responsavelFinanceiro
            );

            usuarioUnidadeDAO.salvar(vinculo);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe IDs e opções numéricas válidas.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar vínculo: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR VÍNCULOS
    // ==========================================

    private static void listarVinculos(
            UsuarioUnidadeDAO usuarioUnidadeDAO) {

        System.out.println("\n--- VÍNCULOS CADASTRADOS ---");

        try {

            List<UsuarioUnidade> vinculos =
                    usuarioUnidadeDAO.listarTodos();

            if (vinculos.isEmpty()) {

                System.out.println("Nenhum vínculo cadastrado.");

            } else {

                for (UsuarioUnidade vinculo : vinculos) {

                    System.out.println("------------------------------");

                    System.out.println("ID do vínculo: " + vinculo.getId());

                    System.out.println(
                            "Usuário: " + vinculo.getUsuario().getNome()
                    );

                    System.out.println(
                            "Unidade: "
                                    + vinculo.getUnidade().getBloco()
                                    + "-"
                                    + vinculo.getUnidade().getNumero()
                    );

                    System.out.println(
                            "Tipo de vínculo: " + vinculo.getTipoVinculo()
                    );

                    System.out.println(
                            "Data de início: " + vinculo.getDataInicio()
                    );

                    System.out.println(
                            "Responsável financeiro: "
                                    + vinculo.getResponsavelFinanceiro()
                    );
                }

                System.out.println("------------------------------");
                System.out.println("Total de vínculos: " + vinculos.size());
            }

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar vínculos: " + e.getMessage());
        }
    }


    // ==========================================
    // ATUALIZAR VÍNCULO
    // ==========================================

    private static void atualizarVinculo(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            UsuarioUnidadeDAO usuarioUnidadeDAO) {

        System.out.println("\n--- ATUALIZAR VÍNCULO ---");

        // Exibe os vínculos cadastrados
        listarVinculos(usuarioUnidadeDAO);

        try {

            System.out.print("\nDigite o ID do vínculo que deseja atualizar: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca o vínculo pelo ID
            UsuarioUnidade vinculo = usuarioUnidadeDAO.buscarPorId(id);

            if (vinculo == null) {
                System.out.println("Vínculo não encontrado!");
                return;
            }

            System.out.println("\nVínculo selecionado:");
            System.out.println("Usuário: " + vinculo.getUsuario().getNome());
            System.out.println(
                    "Unidade: " + vinculo.getUnidade().getBloco()
                            + "-" + vinculo.getUnidade().getNumero()
            );

            // Exibe usuários disponíveis
            System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
            listarUsuarios(usuarioDAO);

            System.out.print("\nDigite o novo ID do usuário: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            // Exibe unidades disponíveis
            System.out.println("\n--- UNIDADES DISPONÍVEIS ---");
            listarUnidades(unidadeDAO);

            System.out.print("\nDigite o novo ID da unidade: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            // Busca os novos registros
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (usuario == null || unidade == null) {
                System.out.println("Usuário ou unidade não encontrado!");
                return;
            }

            // Escolha do tipo de vínculo
            System.out.println("\nTipos de vínculo disponíveis:");
            System.out.println("1 - PROPRIETARIO");
            System.out.println("2 - INQUILINO");
            System.out.println("3 - MORADOR");

            System.out.print("Escolha o novo tipo de vínculo: ");
            int tipoEscolhido = Integer.parseInt(leitura.nextLine());

            TipoVinculo tipoVinculo;

            switch (tipoEscolhido) {
                case 1:
                    tipoVinculo = TipoVinculo.PROPRIETARIO;
                    break;

                case 2:
                    tipoVinculo = TipoVinculo.INQUILINO;
                    break;

                case 3:
                    tipoVinculo = TipoVinculo.MORADOR;
                    break;

                default:
                    System.out.println("Tipo de vínculo inválido!");
                    return;
            }

            // Responsabilidade financeira
            System.out.print("É responsável financeiro? (S/N): ");
            boolean responsavelFinanceiro =
                    leitura.nextLine().equalsIgnoreCase("S");

            // Atualiza os dados do vínculo existente
            vinculo.setUsuario(usuario);
            vinculo.setUnidade(unidade);
            vinculo.setTipoVinculo(tipoVinculo);
            vinculo.setResponsavelFinanceiro(responsavelFinanceiro);

            // Persiste as alterações
            usuarioUnidadeDAO.atualizar(vinculo);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe IDs e opções numéricas válidas.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar vínculo: " + e.getMessage());
        }
    }


    // ==========================================
    // EXCLUIR VÍNCULO
    // ==========================================

    private static void excluirVinculo(
            Scanner leitura,
            UsuarioUnidadeDAO usuarioUnidadeDAO) {

        System.out.println("\n--- EXCLUIR VÍNCULO ---");

        // Exibe os vínculos cadastrados
        listarVinculos(usuarioUnidadeDAO);

        try {

            System.out.print("\nDigite o ID do vínculo que deseja excluir: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            // Busca o vínculo pelo ID
            UsuarioUnidade vinculo = usuarioUnidadeDAO.buscarPorId(id);

            if (vinculo == null) {
                System.out.println("Vínculo não encontrado!");
                return;
            }

            // Exibe os dados do vínculo selecionado
            System.out.println("\nVínculo selecionado:");

            System.out.println(
                    "Usuário: " + vinculo.getUsuario().getNome()
            );

            System.out.println(
                    "Unidade: " + vinculo.getUnidade().getBloco()
                            + "-" + vinculo.getUnidade().getNumero()
            );

            System.out.println(
                    "Tipo: " + vinculo.getTipoVinculo()
            );

            // Solicita confirmação
            System.out.print("\nTem certeza que deseja excluir? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                usuarioUnidadeDAO.excluir(id);

            } else {

                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir vínculo: " + e.getMessage());
        }
    }


// ==========================================
// CADASTRAR OCORRÊNCIA
// ==========================================

    private static void cadastrarOcorrencia(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            OcorrenciaDAO ocorrenciaDAO) {

        System.out.println("\n--- CADASTRAR OCORRÊNCIA ---");

        try {

            System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
            listarUsuarios(usuarioDAO);

            System.out.print("\nID do usuário que está registrando: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            System.out.println("\n--- UNIDADES DISPONÍVEIS ---");
            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade (0 se não houver): ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade = null;

            if (idUnidade != 0) {
                unidade = unidadeDAO.buscarPorId(idUnidade);

                if (unidade == null) {
                    System.out.println("Unidade não encontrada!");
                    return;
                }
            }

            CategoriaOcorrencia categoria = selecionarCategoria(leitura);

            if (categoria == null) {
                return;
            }

            System.out.print("\nTítulo: ");
            String titulo = leitura.nextLine();

            System.out.print("Descrição: ");
            String descricao = leitura.nextLine();

            Ocorrencia ocorrencia = new Ocorrencia();

            ocorrencia.setUsuarioRegistro(usuario);
            ocorrencia.setUnidade(unidade);
            ocorrencia.setCategoria(categoria);
            ocorrencia.setTitulo(titulo);
            ocorrencia.setDescricao(descricao);
            ocorrencia.setStatus(StatusOcorrencia.ABERTA);

            ocorrenciaDAO.salvar(ocorrencia);

        } catch (NumberFormatException e) {

            System.out.println("Informe IDs numéricos válidos.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar ocorrência: " + e.getMessage());
        }
    }


// ==========================================
// SELECIONAR CATEGORIA
// ==========================================


    private static CategoriaOcorrencia selecionarCategoria(Scanner leitura) {

        System.out.println("\n--- CATEGORIAS ---");
        System.out.println("1 - PORTARIA");
        System.out.println("2 - SEGURANCA");
        System.out.println("3 - CONVIVENCIA");
        System.out.println("4 - INFRAESTRUTURA");
        System.out.println("5 - OUTRA");

        System.out.print("Escolha a categoria: ");

        int opcao = lerOpcao(leitura);

        switch (opcao) {
            case 1:
                return CategoriaOcorrencia.PORTARIA;
            case 2:
                return CategoriaOcorrencia.SEGURANCA;
            case 3:
                return CategoriaOcorrencia.CONVIVENCIA;
            case 4:
                return CategoriaOcorrencia.INFRAESTRUTURA;
            case 5:
                return CategoriaOcorrencia.OUTRA;
            default:
                System.out.println("Categoria inválida!");
                return null;
        }
    }



// ==========================================
// LISTAR OCORRÊNCIAS
// ==========================================

    private static void listarOcorrencias(OcorrenciaDAO ocorrenciaDAO) {

        System.out.println("\n--- OCORRÊNCIAS CADASTRADAS ---");

        try {

            List<Ocorrencia> ocorrencias = ocorrenciaDAO.listarTodos();

            if (ocorrencias.isEmpty()) {
                System.out.println("Nenhuma ocorrência cadastrada.");
                return;
            }

            for (Ocorrencia ocorrencia : ocorrencias) {

                System.out.println("------------------------------");
                System.out.println("ID: " + ocorrencia.getId());
                System.out.println("Título: " + ocorrencia.getTitulo());
                System.out.println("Categoria: " + ocorrencia.getCategoria());
                System.out.println("Usuário: "
                        + ocorrencia.getUsuarioRegistro().getNome());

                if (ocorrencia.getUnidade() != null) {
                    System.out.println("Unidade: "
                            + ocorrencia.getUnidade().getBloco()
                            + "-"
                            + ocorrencia.getUnidade().getNumero());
                } else {
                    System.out.println("Unidade: Não informada");
                }

                System.out.println("Status: " + ocorrencia.getStatus());
                System.out.println("Data: " + ocorrencia.getRegistradaEm());
            }

            System.out.println("------------------------------");
            System.out.println("Total de ocorrências: " + ocorrencias.size());

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar ocorrências: " + e.getMessage());
        }
    }


// ==========================================
// CONSULTAR OCORRÊNCIA POR ID
// ==========================================

    private static void consultarOcorrencia(
            Scanner leitura,
            OcorrenciaDAO ocorrenciaDAO) {

        try {

            System.out.print("\nDigite o ID da ocorrência: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Ocorrencia ocorrencia = ocorrenciaDAO.buscarPorId(id);

            if (ocorrencia == null) {
                System.out.println("Ocorrência não encontrada!");
                return;
            }

            System.out.println("\n--- DETALHES DA OCORRÊNCIA ---");
            System.out.println("ID: " + ocorrencia.getId());
            System.out.println("Título: " + ocorrencia.getTitulo());
            System.out.println("Descrição: " + ocorrencia.getDescricao());
            System.out.println("Categoria: " + ocorrencia.getCategoria());
            System.out.println("Status: " + ocorrencia.getStatus());
            System.out.println("Registrada por: "
                    + ocorrencia.getUsuarioRegistro().getNome());
            System.out.println("Data: " + ocorrencia.getRegistradaEm());

            if (ocorrencia.getUnidade() != null) {
                System.out.println("Unidade: "
                        + ocorrencia.getUnidade().getBloco()
                        + "-"
                        + ocorrencia.getUnidade().getNumero());
            } else {
                System.out.println("Unidade: Não informada");
            }

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar ocorrência: " + e.getMessage());
        }
    }


// ==========================================
// ATUALIZAR OCORRÊNCIA
// ==========================================

    private static void atualizarOcorrencia(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            OcorrenciaDAO ocorrenciaDAO) {

        System.out.println("\n--- ATUALIZAR OCORRÊNCIA ---");

        listarOcorrencias(ocorrenciaDAO);

        try {

            System.out.print("\nDigite o ID da ocorrência: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Ocorrencia ocorrencia = ocorrenciaDAO.buscarPorId(id);

            if (ocorrencia == null) {
                System.out.println("Ocorrência não encontrada!");
                return;
            }

            System.out.print("Novo título: ");
            ocorrencia.setTitulo(leitura.nextLine());

            System.out.print("Nova descrição: ");
            ocorrencia.setDescricao(leitura.nextLine());

            CategoriaOcorrencia categoria = selecionarCategoria(leitura);

            if (categoria == null) {
                return;
            }

            ocorrencia.setCategoria(categoria);

            System.out.println("\n--- STATUS ---");
            System.out.println("1 - ABERTA");
            System.out.println("2 - EM_ANALISE");
            System.out.println("3 - RESOLVIDA");
            System.out.println("4 - CANCELADA");

            System.out.println("\n--- STATUS ---");
            System.out.println("1 - ABERTA");
            System.out.println("2 - EM_ANALISE");
            System.out.println("3 - RESOLVIDA");
            System.out.println("4 - CANCELADA");

            System.out.print("Escolha o novo status: ");

            int opcaoStatus = lerOpcao(leitura);

            switch (opcaoStatus) {
                case 1:
                    ocorrencia.setStatus(StatusOcorrencia.ABERTA);
                    break;
                case 2:
                    ocorrencia.setStatus(StatusOcorrencia.EM_ANALISE);
                    break;
                case 3:
                    ocorrencia.setStatus(StatusOcorrencia.RESOLVIDA);
                    break;
                case 4:
                    ocorrencia.setStatus(StatusOcorrencia.CANCELADA);
                    break;
                default:
                    System.out.println("Status inválido!");
                    return;
            }
            System.out.print("ID da nova unidade (0 para nenhuma): ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            if (idUnidade == 0) {
                ocorrencia.setUnidade(null);
            } else {
                UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

                if (unidade == null) {
                    System.out.println("Unidade não encontrada!");
                    return;
                }

                ocorrencia.setUnidade(unidade);
            }

            ocorrenciaDAO.atualizar(ocorrencia);

        } catch (NumberFormatException e) {

            System.out.println("Informe valores numéricos válidos.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar ocorrência: " + e.getMessage());
        }
    }


// ==========================================
// EXCLUIR OCORRÊNCIA
// ==========================================

    private static void excluirOcorrencia(
            Scanner leitura,
            OcorrenciaDAO ocorrenciaDAO) {

        System.out.println("\n--- EXCLUIR OCORRÊNCIA ---");

        listarOcorrencias(ocorrenciaDAO);

        try {

            System.out.print("\nDigite o ID da ocorrência: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Ocorrencia ocorrencia = ocorrenciaDAO.buscarPorId(id);

            if (ocorrencia == null) {
                System.out.println("Ocorrência não encontrada!");
                return;
            }

            System.out.println("\nOcorrência selecionada:");
            System.out.println("Título: " + ocorrencia.getTitulo());
            System.out.println("Categoria: " + ocorrencia.getCategoria());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                ocorrenciaDAO.excluir(id);
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir ocorrência: " + e.getMessage());
        }
    }



    private static void menuVisitantes(
            Scanner leitura,
            VisitanteDAO visitanteDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE VISITANTES =====");
            System.out.println("1 - Cadastrar visitante");
            System.out.println("2 - Listar visitantes");
            System.out.println("3 - Consultar visitante por ID");
            System.out.println("4 - Atualizar visitante");
            System.out.println("5 - Excluir visitante");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarVisitante(leitura, visitanteDAO);
                    break;

                case 2:
                    listarVisitantes(visitanteDAO);
                    break;

                case 3:
                    consultarVisitante(leitura, visitanteDAO);
                    break;

                case 4:
                    atualizarVisitante(leitura, visitanteDAO);
                    break;

                case 5:
                    excluirVisitante(leitura, visitanteDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


// ==========================================
// CADASTRAR VISITANTE
// ==========================================

    private static void cadastrarVisitante(
            Scanner leitura,
            VisitanteDAO visitanteDAO) {

        System.out.println("\n--- CADASTRAR VISITANTE ---");

        System.out.print("Nome: ");
        String nome = leitura.nextLine();

        System.out.print("Documento: ");
        String documento = leitura.nextLine();

        System.out.print("Telefone: ");
        String telefone = leitura.nextLine();

        System.out.print("Observações: ");
        String observacoes = leitura.nextLine();

        Visitante visitante = new Visitante();

        visitante.setNome(nome);
        visitante.setDocumento(documento.isBlank() ? null : documento);
        visitante.setTelefone(telefone.isBlank() ? null : telefone);
        visitante.setObservacoes(observacoes.isBlank() ? null : observacoes);

        visitanteDAO.salvar(visitante);
    }


// ==========================================
// LISTAR VISITANTES
// ==========================================

    private static void listarVisitantes(VisitanteDAO visitanteDAO) {

        System.out.println("\n--- VISITANTES CADASTRADOS ---");

        try {

            List<Visitante> visitantes = visitanteDAO.listarTodos();

            if (visitantes.isEmpty()) {
                System.out.println("Nenhum visitante cadastrado.");
                return;
            }

            for (Visitante visitante : visitantes) {

                System.out.println("------------------------------");
                System.out.println("ID: " + visitante.getId());
                System.out.println("Nome: " + visitante.getNome());
                System.out.println("Documento: " + visitante.getDocumento());
                System.out.println("Telefone: " + visitante.getTelefone());
                System.out.println("Observações: " + visitante.getObservacoes());
            }

            System.out.println("------------------------------");
            System.out.println("Total de visitantes: " + visitantes.size());

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar visitantes: " + e.getMessage());
        }
    }


// ==========================================
// CONSULTAR VISITANTE POR ID
// ==========================================

    private static void consultarVisitante(
            Scanner leitura,
            VisitanteDAO visitanteDAO) {

        try {

            System.out.print("\nDigite o ID do visitante: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Visitante visitante = visitanteDAO.buscarPorId(id);

            if (visitante == null) {
                System.out.println("Visitante não encontrado!");
                return;
            }

            System.out.println("\n--- DETALHES DO VISITANTE ---");
            System.out.println("ID: " + visitante.getId());
            System.out.println("Nome: " + visitante.getNome());
            System.out.println("Documento: " + visitante.getDocumento());
            System.out.println("Telefone: " + visitante.getTelefone());
            System.out.println("Observações: " + visitante.getObservacoes());

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar visitante: " + e.getMessage());
        }
    }


// ==========================================
// ATUALIZAR VISITANTE
// ==========================================

    private static void atualizarVisitante(
            Scanner leitura,
            VisitanteDAO visitanteDAO) {

        System.out.println("\n--- ATUALIZAR VISITANTE ---");

        listarVisitantes(visitanteDAO);

        try {

            System.out.print("\nDigite o ID do visitante: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Visitante visitante = visitanteDAO.buscarPorId(id);

            if (visitante == null) {
                System.out.println("Visitante não encontrado!");
                return;
            }

            System.out.print("Novo nome: ");
            visitante.setNome(leitura.nextLine());

            System.out.print("Novo documento: ");
            visitante.setDocumento(leitura.nextLine());

            System.out.print("Novo telefone: ");
            visitante.setTelefone(leitura.nextLine());

            System.out.print("Novas observações: ");
            visitante.setObservacoes(leitura.nextLine());

            visitanteDAO.atualizar(visitante);

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar visitante: " + e.getMessage());
        }
    }


// ==========================================
// EXCLUIR VISITANTE
// ==========================================

    private static void excluirVisitante(
            Scanner leitura,
            VisitanteDAO visitanteDAO) {

        System.out.println("\n--- EXCLUIR VISITANTE ---");

        listarVisitantes(visitanteDAO);

        try {

            System.out.print("\nDigite o ID do visitante: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Visitante visitante = visitanteDAO.buscarPorId(id);

            if (visitante == null) {
                System.out.println("Visitante não encontrado!");
                return;
            }

            System.out.println("\nVisitante selecionado:");
            System.out.println("Nome: " + visitante.getNome());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                visitanteDAO.excluir(id);

            } else {

                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir visitante: " + e.getMessage());
        }

    }


// ==========================================
// MENU DE AUTORIZAÇÕES DE VISITANTES
// ==========================================

    private static void menuAutorizacoes(
            Scanner leitura,
            VisitanteDAO visitanteDAO,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE AUTORIZAÇÕES =====");
            System.out.println("1 - Cadastrar autorização");
            System.out.println("2 - Listar autorizações");
            System.out.println("3 - Consultar autorização por ID");
            System.out.println("4 - Atualizar autorização");
            System.out.println("5 - Excluir autorização");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    cadastrarAutorizacao(
                            leitura, visitanteDAO, unidadeDAO,
                            usuarioDAO, autorizacaoDAO
                    );
                    break;

                case 2:
                    listarAutorizacoes(autorizacaoDAO);
                    break;

                case 3:
                    consultarAutorizacao(leitura, autorizacaoDAO);
                    break;

                case 4:
                    atualizarAutorizacao(leitura, autorizacaoDAO);
                    break;

                case 5:
                    excluirAutorizacao(leitura, autorizacaoDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
// CADASTRAR AUTORIZAÇÃO
// ==========================================

    private static void cadastrarAutorizacao(
            Scanner leitura,
            VisitanteDAO visitanteDAO,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        System.out.println("\n--- CADASTRAR AUTORIZAÇÃO ---");

        try {
            System.out.println("\n--- VISITANTES DISPONÍVEIS ---");
            listarVisitantes(visitanteDAO);

            System.out.print("\nID do visitante: ");
            Integer idVisitante = Integer.parseInt(leitura.nextLine());

            Visitante visitante = visitanteDAO.buscarPorId(idVisitante);

            if (visitante == null) {
                System.out.println("Visitante não encontrado!");
                return;
            }

            System.out.println("\n--- UNIDADES DISPONÍVEIS ---");
            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            System.out.println("\n--- USUÁRIOS DISPONÍVEIS ---");
            listarUsuarios(usuarioDAO);

            System.out.print("\nID do usuário autorizador: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            System.out.print("\nInício da validade (dd/MM/yyyy HH:mm): ");
            LocalDateTime inicio = LocalDateTime.parse(
                    leitura.nextLine(), formato
            );

            System.out.print("Fim da validade (dd/MM/yyyy HH:mm ou ENTER): ");
            String fimTexto = leitura.nextLine();

            LocalDateTime fim = fimTexto.isBlank()
                    ? null
                    : LocalDateTime.parse(fimTexto, formato);

            if (fim != null && fim.isBefore(inicio)) {
                System.out.println("O fim da validade não pode ser anterior ao início!");
                return;
            }

            System.out.print("Quantidade de acessos (ou ENTER para ilimitado): ");
            String quantidadeTexto = leitura.nextLine();

            Integer quantidade = quantidadeTexto.isBlank()
                    ? null
                    : Integer.parseInt(quantidadeTexto);

            if (quantidade != null && quantidade <= 0) {
                System.out.println("A quantidade de acessos deve ser maior que zero!");
                return;
            }

            System.out.print("Observações: ");
            String observacoes = leitura.nextLine();

            AutorizacaoVisitante autorizacao = new AutorizacaoVisitante();

            autorizacao.setVisitante(visitante);
            autorizacao.setUnidade(unidade);
            autorizacao.setUsuarioAutorizador(usuario);
            autorizacao.setInicioValidade(inicio);
            autorizacao.setFimValidade(fim);
            autorizacao.setQuantidadeAcessos(quantidade);
            autorizacao.setStatus(StatusAutorizacao.PENDENTE);
            autorizacao.setObservacoes(
                    observacoes.isBlank() ? null : observacoes
            );

            autorizacaoDAO.salvar(autorizacao);

        } catch (NumberFormatException e) {
            System.out.println("Informe IDs e quantidades numéricas válidas.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Data inválida! Utilize o formato dd/MM/yyyy HH:mm.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar autorização: " + e.getMessage());
        }
    }

    // ==========================================
// LISTAR AUTORIZAÇÕES
// ==========================================

    private static void listarAutorizacoes(
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        System.out.println("\n--- AUTORIZAÇÕES CADASTRADAS ---");

        try {
            List<AutorizacaoVisitante> autorizacoes =
                    autorizacaoDAO.listarTodos();

            if (autorizacoes.isEmpty()) {
                System.out.println("Nenhuma autorização cadastrada.");
                return;
            }

            for (AutorizacaoVisitante autorizacao : autorizacoes) {

                System.out.println("------------------------------");
                System.out.println("ID: " + autorizacao.getId());
                System.out.println("Visitante: "
                        + autorizacao.getVisitante().getNome());
                System.out.println("Unidade: "
                        + autorizacao.getUnidade().getBloco()
                        + "-"
                        + autorizacao.getUnidade().getNumero());
                System.out.println("Autorizado por: "
                        + autorizacao.getUsuarioAutorizador().getNome());
                System.out.println("Início: " + autorizacao.getInicioValidade());
                System.out.println("Fim: "
                        + (autorizacao.getFimValidade() == null
                        ? "Não informado"
                        : autorizacao.getFimValidade()));
                System.out.println("Quantidade de acessos: "
                        + (autorizacao.getQuantidadeAcessos() == null
                        ? "Ilimitada"
                        : autorizacao.getQuantidadeAcessos()));
                System.out.println("Status: " + autorizacao.getStatus());
                System.out.println("Observações: "
                        + autorizacao.getObservacoes());
            }

            System.out.println("------------------------------");
            System.out.println("Total de autorizações: " + autorizacoes.size());

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar autorizações: " + e.getMessage());
        }
    }

    // ==========================================
// CONSULTAR AUTORIZAÇÃO POR ID
// ==========================================

    private static void consultarAutorizacao(
            Scanner leitura,
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        try {
            System.out.print("\nDigite o ID da autorização: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            AutorizacaoVisitante autorizacao =
                    autorizacaoDAO.buscarPorId(id);

            if (autorizacao == null) {
                System.out.println("Autorização não encontrada!");
                return;
            }

            System.out.println("\n--- DETALHES DA AUTORIZAÇÃO ---");
            System.out.println("ID: " + autorizacao.getId());
            System.out.println("Visitante: "
                    + autorizacao.getVisitante().getNome());
            System.out.println("Unidade: "
                    + autorizacao.getUnidade().getBloco()
                    + "-"
                    + autorizacao.getUnidade().getNumero());
            System.out.println("Autorizado por: "
                    + autorizacao.getUsuarioAutorizador().getNome());
            System.out.println("Início da validade: "
                    + autorizacao.getInicioValidade());
            System.out.println("Fim da validade: "
                    + autorizacao.getFimValidade());
            System.out.println("Quantidade de acessos: "
                    + (autorizacao.getQuantidadeAcessos() == null
                    ? "Ilimitada"
                    : autorizacao.getQuantidadeAcessos()));
            System.out.println("Status: " + autorizacao.getStatus());
            System.out.println("Observações: " + autorizacao.getObservacoes());

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao consultar autorização: " + e.getMessage());
        }
    }

    // ==========================================
// ATUALIZAR AUTORIZAÇÃO
// ==========================================

    private static void atualizarAutorizacao(
            Scanner leitura,
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        System.out.println("\n--- ATUALIZAR AUTORIZAÇÃO ---");

        listarAutorizacoes(autorizacaoDAO);

        try {
            System.out.print("\nDigite o ID da autorização: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            AutorizacaoVisitante autorizacao =
                    autorizacaoDAO.buscarPorId(id);

            if (autorizacao == null) {
                System.out.println("Autorização não encontrada!");
                return;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            System.out.println("\nAutorização selecionada: "
                    + autorizacao.getVisitante().getNome());

            System.out.print("Novo início (dd/MM/yyyy HH:mm ou ENTER para manter): ");
            String inicioTexto = leitura.nextLine();

            if (!inicioTexto.isBlank()) {
                autorizacao.setInicioValidade(
                        LocalDateTime.parse(inicioTexto, formato)
                );
            }

            System.out.print("Novo fim (dd/MM/yyyy HH:mm, ENTER para manter ou 0 para limpar): ");
            String fimTexto = leitura.nextLine();

            if (fimTexto.equals("0")) {
                autorizacao.setFimValidade(null);
            } else if (!fimTexto.isBlank()) {
                autorizacao.setFimValidade(
                        LocalDateTime.parse(fimTexto, formato)
                );
            }

            if (autorizacao.getFimValidade() != null
                    && autorizacao.getFimValidade()
                    .isBefore(autorizacao.getInicioValidade())) {

                System.out.println("O fim da validade não pode ser anterior ao início!");
                return;
            }

            System.out.print("Nova quantidade de acessos (ENTER para manter ou 0 para ilimitado): ");
            String quantidadeTexto = leitura.nextLine();

            if (quantidadeTexto.equals("0")) {
                autorizacao.setQuantidadeAcessos(null);
            } else if (!quantidadeTexto.isBlank()) {
                int quantidade = Integer.parseInt(quantidadeTexto);

                if (quantidade < 1) {
                    System.out.println("A quantidade deve ser maior que zero!");
                    return;
                }

                autorizacao.setQuantidadeAcessos(quantidade);
            }

            System.out.println("\n--- STATUS DISPONÍVEIS ---");
            System.out.println("1 - PENDENTE");
            System.out.println("2 - APROVADA");
            System.out.println("3 - CANCELADA");
            System.out.println("4 - EXPIRADA");

            System.out.print("Escolha o novo status: ");
            int opcaoStatus = lerOpcao(leitura);

            switch (opcaoStatus) {
                case 1:
                    autorizacao.setStatus(StatusAutorizacao.PENDENTE);
                    break;
                case 2:
                    autorizacao.setStatus(StatusAutorizacao.APROVADA);
                    break;
                case 3:
                    autorizacao.setStatus(StatusAutorizacao.CANCELADA);
                    break;
                case 4:
                    autorizacao.setStatus(StatusAutorizacao.EXPIRADA);
                    break;
                default:
                    System.out.println("Status inválido!");
                    return;
            }

            System.out.print("Novas observações (ENTER para limpar): ");
            String observacoes = leitura.nextLine();

            autorizacao.setObservacoes(
                    observacoes.isBlank() ? null : observacoes
            );

            autorizacaoDAO.atualizar(autorizacao);

        } catch (NumberFormatException e) {
            System.out.println("Informe valores numéricos válidos.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Data inválida! Utilize dd/MM/yyyy HH:mm.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar autorização: " + e.getMessage());
        }
    }

    // ==========================================
// EXCLUIR AUTORIZAÇÃO
// ==========================================

    private static void excluirAutorizacao(
            Scanner leitura,
            AutorizacaoVisitanteDAO autorizacaoDAO) {

        System.out.println("\n--- EXCLUIR AUTORIZAÇÃO ---");

        listarAutorizacoes(autorizacaoDAO);

        try {
            System.out.print("\nDigite o ID da autorização: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            AutorizacaoVisitante autorizacao =
                    autorizacaoDAO.buscarPorId(id);

            if (autorizacao == null) {
                System.out.println("Autorização não encontrada!");
                return;
            }

            System.out.println("\nAutorização selecionada:");
            System.out.println("Visitante: "
                    + autorizacao.getVisitante().getNome());
            System.out.println("Status: " + autorizacao.getStatus());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                autorizacaoDAO.excluir(id);
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir autorização: " + e.getMessage());
            System.out.println(
                    "Verifique se existem registros de acesso associados a esta autorização."
            );
        }
    }


    // ==========================================
    // MENU DE REGISTROS DE ACESSO
    // ==========================================

    private static void menuRegistrosAcesso(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO,
            AutorizacaoVisitanteDAO autorizacaoDAO,
            FuncionarioDAO funcionarioDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== GESTÃO DE REGISTROS DE ACESSO =====");
            System.out.println("1 - Registrar entrada de visitante");
            System.out.println("2 - Listar registros de acesso");
            System.out.println("3 - Consultar registro por ID");
            System.out.println("4 - Registrar saída de visitante");
            System.out.println("5 - Atualizar observações");
            System.out.println("6 - Excluir registro");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    registrarEntrada(
                            leitura, registroAcessoDAO,
                            autorizacaoDAO, funcionarioDAO);
                    break;

                case 2:
                    listarRegistrosAcesso(registroAcessoDAO);
                    break;

                case 3:
                    consultarRegistroAcesso(leitura, registroAcessoDAO);
                    break;

                case 4:
                    registrarSaida(leitura, registroAcessoDAO);
                    break;

                case 5:
                    atualizarObservacoesAcesso(leitura, registroAcessoDAO);
                    break;

                case 6:
                    excluirRegistroAcesso(leitura, registroAcessoDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ==========================================
    // REGISTRAR ENTRADA
    // ==========================================

    private static void registrarEntrada(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO,
            AutorizacaoVisitanteDAO autorizacaoDAO,
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- REGISTRAR ENTRADA ---");

        try {

            List<AutorizacaoVisitante> autorizacoes =
                    autorizacaoDAO.listarTodos();

            List<RegistroAcesso> registros =
                    registroAcessoDAO.listarTodos();

            LocalDateTime agora = LocalDateTime.now();

            System.out.println("\n--- AUTORIZAÇÕES DISPONÍVEIS ---");

            boolean encontrouAutorizacao = false;

            for (AutorizacaoVisitante autorizacao : autorizacoes) {

                boolean aprovada =
                        autorizacao.getStatus() == StatusAutorizacao.APROVADA;

                boolean dentroDaValidade =
                        autorizacao.getInicioValidade() != null
                                && !agora.isBefore(autorizacao.getInicioValidade())
                                && (autorizacao.getFimValidade() == null
                                || !agora.isAfter(autorizacao.getFimValidade()));

                int quantidadeUtilizada = 0;

                for (RegistroAcesso registro : registros) {
                    if (registro.getAutorizacao().getId()
                            .equals(autorizacao.getId())) {
                        quantidadeUtilizada++;
                    }
                }

                boolean possuiAcessosDisponiveis =
                        autorizacao.getQuantidadeAcessos() == null
                                || quantidadeUtilizada
                                < autorizacao.getQuantidadeAcessos();

                if (aprovada && dentroDaValidade
                        && possuiAcessosDisponiveis) {

                    encontrouAutorizacao = true;

                    System.out.println("------------------------------");
                    System.out.println("ID: " + autorizacao.getId());
                    System.out.println("Visitante: "
                            + autorizacao.getVisitante().getNome());
                    System.out.println("Unidade: "
                            + autorizacao.getUnidade().getBloco()
                            + "-"
                            + autorizacao.getUnidade().getNumero());
                    System.out.println("Acessos utilizados: "
                            + quantidadeUtilizada);
                    System.out.println("Limite: "
                            + (autorizacao.getQuantidadeAcessos() == null
                            ? "Ilimitado"
                            : autorizacao.getQuantidadeAcessos()));
                }
            }

            if (!encontrouAutorizacao) {
                System.out.println(
                        "Nenhuma autorização aprovada e disponível no momento.");
                return;
            }

            System.out.print("\nDigite o ID da autorização: ");
            Integer idAutorizacao =
                    Integer.parseInt(leitura.nextLine());

            AutorizacaoVisitante autorizacao =
                    autorizacaoDAO.buscarPorId(idAutorizacao);

            if (autorizacao == null
                    || autorizacao.getStatus() != StatusAutorizacao.APROVADA) {

                System.out.println("Autorização inválida ou não aprovada!");
                return;
            }

            boolean dentroDaValidade =
                    autorizacao.getInicioValidade() != null
                            && !agora.isBefore(autorizacao.getInicioValidade())
                            && (autorizacao.getFimValidade() == null
                            || !agora.isAfter(autorizacao.getFimValidade()));

            if (!dentroDaValidade) {
                System.out.println("A autorização está fora do período de validade!");
                return;
            }

            int quantidadeUtilizada = 0;

            for (RegistroAcesso registro : registros) {
                if (registro.getAutorizacao().getId()
                        .equals(autorizacao.getId())) {
                    quantidadeUtilizada++;
                }
            }

            if (autorizacao.getQuantidadeAcessos() != null
                    && quantidadeUtilizada >= autorizacao.getQuantidadeAcessos()) {

                System.out.println("Limite de acessos desta autorização atingido!");
                return;
            }

            System.out.println("\n--- FUNCIONÁRIOS DA PORTARIA ---");

            List<Funcionario> funcionarios =
                    funcionarioDAO.listarTodos();

            boolean encontrouFuncionario = false;

            for (Funcionario funcionario : funcionarios) {

                if (funcionario.getDataDemissao() == null) {

                    encontrouFuncionario = true;

                    System.out.println("------------------------------");
                    System.out.println("ID: " + funcionario.getId());
                    System.out.println("Nome: "
                            + funcionario.getUsuario().getNome());
                    System.out.println("Cargo: "
                            + funcionario.getCargo().getNome());
                }
            }

            if (!encontrouFuncionario) {
                System.out.println("Nenhum funcionário ativo cadastrado.");
            }

            System.out.print(
                    "\nID do funcionário da portaria (0 para não informar): ");

            Integer idFuncionario =
                    Integer.parseInt(leitura.nextLine());

            Funcionario funcionarioPortaria = null;

            if (idFuncionario != 0) {

                funcionarioPortaria =
                        funcionarioDAO.buscarPorId(idFuncionario);

                if (funcionarioPortaria == null
                        || funcionarioPortaria.getDataDemissao() != null) {

                    System.out.println("Funcionário inexistente ou inativo!");
                    return;
                }
            }

            System.out.print("Observações: ");
            String observacoes = leitura.nextLine();

            RegistroAcesso registro = new RegistroAcesso();

            registro.setAutorizacao(autorizacao);
            registro.setFuncionarioPortaria(funcionarioPortaria);
            registro.setEntrada(agora);
            registro.setSaida(null);
            registro.setObservacoes(
                    observacoes.isBlank() ? null : observacoes);

            registroAcessoDAO.salvar(registro);

            System.out.println("\nEntrada registrada com sucesso!");
            System.out.println("Visitante: "
                    + autorizacao.getVisitante().getNome());
            System.out.println("Entrada: "
                    + agora.format(DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm")));

        } catch (NumberFormatException e) {

            System.out.println("Informe IDs numéricos válidos.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    // ==========================================
    // LISTAR REGISTROS DE ACESSO
    // ==========================================

    private static void listarRegistrosAcesso(
            RegistroAcessoDAO registroAcessoDAO) {

        System.out.println("\n--- REGISTROS DE ACESSO ---");

        try {

            List<RegistroAcesso> registros =
                    registroAcessoDAO.listarTodos();

            if (registros.isEmpty()) {
                System.out.println("Nenhum registro de acesso encontrado.");
                return;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (RegistroAcesso registro : registros) {

                System.out.println("------------------------------");
                System.out.println("ID: " + registro.getId());

                System.out.println("Visitante: "
                        + registro.getAutorizacao()
                        .getVisitante().getNome());

                System.out.println("Unidade: "
                        + registro.getAutorizacao()
                        .getUnidade().getBloco()
                        + "-"
                        + registro.getAutorizacao()
                        .getUnidade().getNumero());

                System.out.println("Funcionário: "
                        + (registro.getFuncionarioPortaria() == null
                        ? "Não informado"
                        : registro.getFuncionarioPortaria()
                        .getUsuario().getNome()));

                System.out.println("Entrada: "
                        + registro.getEntrada().format(formato));

                System.out.println("Saída: "
                        + (registro.getSaida() == null
                        ? "Ainda não registrada"
                        : registro.getSaida().format(formato)));

                System.out.println("Observações: "
                        + registro.getObservacoes());
            }

            System.out.println("------------------------------");
            System.out.println("Total de registros: " + registros.size());

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar registros: " + e.getMessage());
        }
    }

    // ==========================================
    // CONSULTAR REGISTRO POR ID
    // ==========================================

    private static void consultarRegistroAcesso(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO) {

        try {

            System.out.print("\nDigite o ID do registro: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            RegistroAcesso registro =
                    registroAcessoDAO.buscarPorId(id);

            if (registro == null) {
                System.out.println("Registro não encontrado!");
                return;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            System.out.println("\n--- DETALHES DO REGISTRO ---");
            System.out.println("ID: " + registro.getId());

            System.out.println("Visitante: "
                    + registro.getAutorizacao()
                    .getVisitante().getNome());

            System.out.println("Unidade: "
                    + registro.getAutorizacao()
                    .getUnidade().getBloco()
                    + "-"
                    + registro.getAutorizacao()
                    .getUnidade().getNumero());

            System.out.println("Funcionário: "
                    + (registro.getFuncionarioPortaria() == null
                    ? "Não informado"
                    : registro.getFuncionarioPortaria()
                    .getUsuario().getNome()));

            System.out.println("Entrada: "
                    + registro.getEntrada().format(formato));

            System.out.println("Saída: "
                    + (registro.getSaida() == null
                    ? "Ainda não registrada"
                    : registro.getSaida().format(formato)));

            System.out.println("Observações: "
                    + registro.getObservacoes());

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao consultar registro: " + e.getMessage());
        }
    }

    // ==========================================
    // REGISTRAR SAÍDA
    // ==========================================

    private static void registrarSaida(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO) {

        System.out.println("\n--- REGISTRAR SAÍDA ---");

        try {

            listarRegistrosAcesso(registroAcessoDAO);

            System.out.print("\nDigite o ID do registro: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            RegistroAcesso registro =
                    registroAcessoDAO.buscarPorId(id);

            if (registro == null) {
                System.out.println("Registro não encontrado!");
                return;
            }

            if (registro.getSaida() != null) {
                System.out.println("A saída deste visitante já foi registrada!");
                return;
            }

            LocalDateTime agora = LocalDateTime.now();

            registro.setSaida(agora);

            registroAcessoDAO.atualizar(registro);

            System.out.println("\nSaída registrada com sucesso!");
            System.out.println("Horário: "
                    + agora.format(DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm")));

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao registrar saída: " + e.getMessage());
        }
    }

    // ==========================================
    // ATUALIZAR OBSERVAÇÕES
    // ==========================================

    private static void atualizarObservacoesAcesso(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO) {

        System.out.println("\n--- ATUALIZAR OBSERVAÇÕES ---");

        try {

            System.out.print("Digite o ID do registro: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            RegistroAcesso registro =
                    registroAcessoDAO.buscarPorId(id);

            if (registro == null) {
                System.out.println("Registro não encontrado!");
                return;
            }

            System.out.println("Visitante: "
                    + registro.getAutorizacao()
                    .getVisitante().getNome());

            System.out.println("Observações atuais: "
                    + registro.getObservacoes());

            System.out.print("Novas observações (ENTER para limpar): ");
            String observacoes = leitura.nextLine();

            registro.setObservacoes(
                    observacoes.isBlank() ? null : observacoes);

            registroAcessoDAO.atualizar(registro);

            System.out.println("Observações atualizadas com sucesso!");

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao atualizar observações: "
                    + e.getMessage());
        }
    }

    // ==========================================
    // EXCLUIR REGISTRO DE ACESSO
    // ==========================================

    private static void excluirRegistroAcesso(
            Scanner leitura,
            RegistroAcessoDAO registroAcessoDAO) {

        System.out.println("\n--- EXCLUIR REGISTRO DE ACESSO ---");

        try {

            listarRegistrosAcesso(registroAcessoDAO);

            System.out.print("\nDigite o ID do registro: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            RegistroAcesso registro =
                    registroAcessoDAO.buscarPorId(id);

            if (registro == null) {
                System.out.println("Registro não encontrado!");
                return;
            }

            System.out.println("\nRegistro selecionado:");
            System.out.println("Visitante: "
                    + registro.getAutorizacao()
                    .getVisitante().getNome());

            System.out.print("\nConfirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                registroAcessoDAO.excluir(id);

                System.out.println("Registro excluído com sucesso!");

            } else {

                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao excluir registro: " + e.getMessage());
        }
    }


    // ==========================================
    // LEITURA DAS OPÇÕES DOS MENUS
    // ==========================================

    private static int lerOpcao(Scanner leitura) {

        try {

            return Integer.parseInt(leitura.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }

    private static void menuEspacos(
            Scanner leitura,
            EspacoComumDAO espacoComumDAO,
            ReservaDAO reservaDAO,
            UsuarioDAO usuarioDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n===== LAZER E RESERVAS =====");
            System.out.println("1 - Cadastrar espaço");
            System.out.println("2 - Listar espaços");
            System.out.println("3 - Solicitar reserva");
            System.out.println("4 - Listar reservas");
            System.out.println("5 - Aprovar ou cancelar reserva");
            System.out.println("6 - Excluir reserva");
            System.out.println("0 - Voltar");

            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {
                case 1:
                    cadastrarEspaco(leitura, espacoComumDAO);
                    break;
                case 2:
                    listarEspacos(espacoComumDAO);
                    break;
                case 3:
                    solicitarReserva(leitura, espacoComumDAO, reservaDAO, usuarioDAO);
                    break;
                case 4:
                    listarReservas(reservaDAO);
                    break;
                case 5:
                    gerenciarReserva(leitura, reservaDAO);
                    break;
                case 6:
                    excluirReserva(leitura, reservaDAO);
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarEspaco(
            Scanner leitura,
            EspacoComumDAO espacoComumDAO) {

        System.out.println("\n--- CADASTRAR ESPAÇO ---");

        try {

            System.out.print("Nome do espaço: ");
            String nome = leitura.nextLine();

            System.out.print("Descrição: ");
            String descricao = leitura.nextLine();

            System.out.print("Capacidade de pessoas: ");
            int capacidade = Integer.parseInt(leitura.nextLine());

            System.out.print("Regras de utilização: ");
            String regras = leitura.nextLine();

            if (nome.isBlank() || capacidade <= 0) {
                System.out.println("Informe um nome e uma capacidade válida!");
                return;
            }

            EspacoComum espaco = new EspacoComum();

            espaco.setNome(nome);
            espaco.setDescricao(descricao.isBlank() ? null : descricao);
            espaco.setCapacidade(capacidade);
            espaco.setRegras(regras.isBlank() ? null : regras);
            espaco.setAtivo(true);

            espacoComumDAO.salvar(espaco);

        } catch (NumberFormatException e) {

            System.out.println("Informe uma capacidade numérica válida.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar espaço: " + e.getMessage());
        }
    }

    private static void listarEspacos(EspacoComumDAO espacoComumDAO) {

        System.out.println("\n--- ESPAÇOS CADASTRADOS ---");

        try {

            List<EspacoComum> espacos = espacoComumDAO.listarTodos();

            if (espacos.isEmpty()) {
                System.out.println("Nenhum espaço cadastrado.");
                return;
            }

            for (EspacoComum espaco : espacos) {

                System.out.println("------------------------------");
                System.out.println("ID: " + espaco.getId());
                System.out.println("Nome: " + espaco.getNome());
                System.out.println("Capacidade: " + espaco.getCapacidade());
                System.out.println("Descrição: " + espaco.getDescricao());
                System.out.println("Regras: " + espaco.getRegras());
                System.out.println(
                        "Status: " + (espaco.getAtivo() ? "Ativo" : "Inativo")
                );
            }

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar espaços: " + e.getMessage());
        }
    }


    private static void solicitarReserva(
            Scanner leitura,
            EspacoComumDAO espacoComumDAO,
            ReservaDAO reservaDAO,
            UsuarioDAO usuarioDAO) {

        System.out.println("\n--- SOLICITAR RESERVA ---");

        try {
            List<EspacoComum> espacos = espacoComumDAO.listarTodos();

            System.out.println("\nESPAÇOS DISPONÍVEIS:");

            for (EspacoComum espaco : espacos) {
                if (Boolean.TRUE.equals(espaco.getAtivo())) {
                    System.out.println(
                            "ID: " + espaco.getId()
                                    + " | " + espaco.getNome()
                                    + " | Capacidade: " + espaco.getCapacidade()
                    );
                }
            }

            System.out.print("\nID do espaço: ");
            int idEspaco = Integer.parseInt(leitura.nextLine());

            System.out.print("ID do usuário solicitante: ");
            int idUsuario = Integer.parseInt(leitura.nextLine());

            System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
            LocalDateTime inicio = LocalDateTime.parse(
                    leitura.nextLine(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            );

            System.out.print("Data e hora de término (dd/MM/yyyy HH:mm): ");
            LocalDateTime fim = LocalDateTime.parse(
                    leitura.nextLine(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            );

            System.out.print("Quantidade de pessoas: ");
            int quantidade = Integer.parseInt(leitura.nextLine());

            System.out.print("Observações: ");
            String observacoes = leitura.nextLine();

            // Verifica se o usuário existe

            EspacoComum espaco = new EspacoComum();
            espaco.setId(idEspaco);

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            Reserva reserva = new Reserva();
            reserva.setEspaco(espaco);
            reserva.setUsuarioSolicitante(usuario);
            reserva.setInicio(inicio);
            reserva.setFim(fim);
            reserva.setQuantidadePessoas(quantidade);
            reserva.setObservacoes(observacoes.isBlank() ? null : observacoes);

            reservaDAO.solicitar(reserva);

        } catch (NumberFormatException e) {
            System.out.println("Informe um número válido.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Formato de data inválido. Utilize dd/MM/yyyy HH:mm.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao solicitar reserva: " + e.getMessage());
        }
    }


    private static void listarReservas(ReservaDAO reservaDAO) {

        System.out.println("\n--- RESERVAS CADASTRADAS ---");

        try {
            List<Reserva> reservas = reservaDAO.listarTodos();

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva cadastrada.");
                return;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Reserva reserva : reservas) {
                System.out.println("\n------------------------------");
                System.out.println("ID: " + reserva.getId());
                System.out.println("Espaço: " + reserva.getEspaco().getNome());
                System.out.println("Solicitante: "
                        + reserva.getUsuarioSolicitante().getId());
                System.out.println("Início: " + reserva.getInicio().format(formato));
                System.out.println("Fim: " + reserva.getFim().format(formato));
                System.out.println("Pessoas: " + reserva.getQuantidadePessoas());
                System.out.println("Status: " + reserva.getStatus());

                if (reserva.getUsuarioAprovador() != null) {
                    System.out.println("Responsável: "
                            + reserva.getUsuarioAprovador().getId());
                }

                System.out.println("Observações: " + reserva.getObservacoes());
            }

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar reservas: " + e.getMessage());
        }
    }


    private static void gerenciarReserva(
            Scanner leitura,
            ReservaDAO reservaDAO) {

        System.out.println("\n--- APROVAR OU CANCELAR RESERVA ---");

        try {
            listarReservas(reservaDAO);

            System.out.print("\nID da reserva: ");
            int idReserva = Integer.parseInt(leitura.nextLine());

            System.out.println("\n1 - Aprovar reserva");
            System.out.println("2 - Cancelar reserva");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao(leitura);

            StatusReserva novoStatus;

            if (opcao == 1) {
                novoStatus = StatusReserva.APROVADA;
            } else if (opcao == 2) {
                novoStatus = StatusReserva.CANCELADA;
            } else {
                System.out.println("Opção inválida!");
                return;
            }

            System.out.print("ID do usuário responsável: ");
            int idResponsavel = Integer.parseInt(leitura.nextLine());

            reservaDAO.alterarStatus(
                    idReserva,
                    novoStatus,
                    idResponsavel
            );

        } catch (NumberFormatException e) {
            System.out.println("Informe um número válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao gerenciar reserva: " + e.getMessage());
        }
    }

    private static void excluirReserva(Scanner leitura, ReservaDAO reservaDAO) {
        System.out.println("\n--- EXCLUIR RESERVA ---");

        listarReservas(reservaDAO);

        System.out.print("\nID da reserva que deseja excluir: ");
        int idReserva = Integer.parseInt(leitura.nextLine());

        System.out.print("Tem certeza que deseja excluir esta reserva? (S/N): ");
        String confirmacao = leitura.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                reservaDAO.excluir(idReserva);
            } catch (Exception e) {
                System.out.println("Erro ao excluir reserva: " + e.getMessage());
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }


    private static void menuEncomendas(
            Scanner leitura,
            EncomendaDAO encomendaDAO,
            UnidadeDAO unidadeDAO,
            FuncionarioDAO funcionarioDAO) {

        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n========== GESTÃO DE ENCOMENDAS ==========");
            System.out.println("1 - Registrar encomenda");
            System.out.println("2 - Listar encomendas");
            System.out.println("3 - Registrar retirada");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {
                case 1:
                    cadastrarEncomenda(leitura, encomendaDAO, unidadeDAO, funcionarioDAO);
                    break;

                case 2:
                    listarEncomendas(encomendaDAO);
                    break;
                case 3:
                    registrarRetirada(leitura, encomendaDAO);
                    break;
                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarEncomenda(
            Scanner leitura,
            EncomendaDAO encomendaDAO,
            UnidadeDAO unidadeDAO,
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- REGISTRAR ENCOMENDA ---");

        try {
            System.out.println("\n--- UNIDADES CADASTRADAS ---");
            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade destinatária: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            System.out.print("ID do funcionário que recebeu (Enter para ignorar): ");
            String idFuncionarioTexto = leitura.nextLine();

            Funcionario funcionario = null;

            if (!idFuncionarioTexto.isBlank()) {
                Integer idFuncionario = Integer.parseInt(idFuncionarioTexto);
                funcionario = funcionarioDAO.buscarPorId(idFuncionario);

                if (funcionario == null) {
                    System.out.println("Funcionário não encontrado!");
                    return;
                }
            }

            Encomenda encomenda = new Encomenda();

            encomenda.setUnidade(unidade);
            encomenda.setFuncionarioRecebimento(funcionario);

            System.out.print("Descrição da encomenda: ");
            encomenda.setDescricao(leitura.nextLine());

            System.out.print("Transportadora: ");
            encomenda.setTransportadora(leitura.nextLine());

            System.out.print("Código de rastreio: ");
            encomenda.setCodigoRastreio(leitura.nextLine());

            System.out.print("Observações: ");
            encomenda.setObservacoes(leitura.nextLine());

            encomendaDAO.salvar(encomenda);

        } catch (NumberFormatException e) {
            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar encomenda: " + e.getMessage());
        }
    }

    private static void listarEncomendas(EncomendaDAO encomendaDAO) {

        System.out.println("\n--- ENCOMENDAS CADASTRADAS ---");

        try {
            List<Encomenda> encomendas = encomendaDAO.listarTodos();

            if (encomendas.isEmpty()) {
                System.out.println("Nenhuma encomenda cadastrada.");
                return;
            }

            for (Encomenda encomenda : encomendas) {
                System.out.println("\n------------------------------");
                System.out.println("ID: " + encomenda.getId());
                System.out.println("Unidade: "
                        + encomenda.getUnidade().getBloco()
                        + " - " + encomenda.getUnidade().getNumero());
                System.out.println("Descrição: " + encomenda.getDescricao());
                System.out.println("Transportadora: " + encomenda.getTransportadora());
                System.out.println("Código de rastreio: " + encomenda.getCodigoRastreio());
                System.out.println("Recebida em: " + encomenda.getRecebidaEm());
                System.out.println("Status: " + encomenda.getStatus());

                if (encomenda.getFuncionarioRecebimento() != null) {
                    System.out.println("Funcionário responsável: "
                            + encomenda.getFuncionarioRecebimento().getId());
                }

                System.out.println("Observações: " + encomenda.getObservacoes());
            }

            System.out.println("\nTotal de encomendas: " + encomendas.size());

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar encomendas: " + e.getMessage());
        }
    }


    private static void registrarRetirada(
            Scanner leitura,
            EncomendaDAO encomendaDAO
    ) {
        System.out.println("\n=== REGISTRAR RETIRADA ===");

        // Exibe as encomendas cadastradas
        listarEncomendas(encomendaDAO);

        try {
            System.out.print("\nInforme o ID da encomenda: ");
            Integer idEncomenda = Integer.parseInt(leitura.nextLine());

            System.out.print("Nome de quem retirou: ");
            String retiradoPor = leitura.nextLine();

            encomendaDAO.registrarRetirada(idEncomenda, retiradoPor);

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar retirada: " + e.getMessage());
        }
    }



    // ==========================================
// MENU DE VAGAS E VEÍCULOS
// ==========================================

    private static void menuVagasVeiculos(
            Scanner leitura,
            VagaDAO vagaDAO,
            VeiculoDAO veiculoDAO,
            UnidadeDAO unidadeDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n========== GESTÃO DE VAGAS E VEÍCULOS ==========");
            System.out.println("1 - Cadastrar vaga");
            System.out.println("2 - Listar vagas");
            System.out.println("3 - Cadastrar veículo");
            System.out.println("4 - Listar veículos");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {

                case 1:
                    cadastrarVaga(leitura, vagaDAO, unidadeDAO);
                    break;

                case 2:
                    listarVagas(vagaDAO);
                    break;

                case 3:
                    cadastrarVeiculo(leitura, veiculoDAO, unidadeDAO, vagaDAO);
                    break;

                case 4:
                    listarVeiculos(veiculoDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    // ==========================================
// CADASTRAR VAGA
// ==========================================

    private static void cadastrarVaga(
            Scanner leitura,
            VagaDAO vagaDAO,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- CADASTRAR VAGA ---");

        try {

            Vaga vaga = new Vaga();

            System.out.print("Identificação da vaga: ");
            vaga.setIdentificacao(leitura.nextLine());

            System.out.println("\n--- TIPOS DE VAGA ---");
            System.out.println("1 - CARRO");
            System.out.println("2 - MOTO");
            System.out.println("3 - BICICLETA");
            System.out.println("4 - OUTRO");

            System.out.print("Escolha o tipo: ");

            int tipo = lerOpcao(leitura);

            switch (tipo) {
                case 1:
                    vaga.setTipo(TipoVaga.CARRO);
                    break;
                case 2:
                    vaga.setTipo(TipoVaga.MOTO);
                    break;
                case 3:
                    vaga.setTipo(TipoVaga.BICICLETA);
                    break;
                case 4:
                    vaga.setTipo(TipoVaga.OUTRO);
                    break;
                default:
                    System.out.println("Tipo inválido!");
                    return;
            }

            System.out.print("A vaga é coberta? (S/N): ");
            String coberta = leitura.nextLine();

            vaga.setCoberta(coberta.equalsIgnoreCase("S"));
            vaga.setStatus(StatusVaga.DISPONIVEL);

            System.out.println("\n--- VINCULAR UNIDADE ---");
            System.out.println("0 - Não vincular a uma unidade");

            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade (0 para ignorar): ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            if (idUnidade != 0) {

                UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

                if (unidade == null) {
                    System.out.println("Unidade não encontrada!");
                    return;
                }

                vaga.setUnidade(unidade);
            }

            vagaDAO.salvar(vaga);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um número válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar vaga: " + e.getMessage());
        }
    }

    // ==========================================
// LISTAR VAGAS
// ==========================================

    private static void listarVagas(VagaDAO vagaDAO) {

        System.out.println("\n--- VAGAS CADASTRADAS ---");

        try {

            List<Vaga> vagas = vagaDAO.listarTodos();

            if (vagas.isEmpty()) {
                System.out.println("Nenhuma vaga cadastrada.");
                return;
            }

            for (Vaga vaga : vagas) {

                System.out.println("\n------------------------------");
                System.out.println("ID: " + vaga.getId());
                System.out.println("Identificação: " + vaga.getIdentificacao());
                System.out.println("Tipo: " + vaga.getTipo());
                System.out.println("Coberta: " + (vaga.isCoberta() ? "Sim" : "Não"));
                System.out.println("Status: " + vaga.getStatus());

                if (vaga.getUnidade() != null) {

                    System.out.println("Unidade: "
                            + vaga.getUnidade().getBloco()
                            + " - "
                            + vaga.getUnidade().getNumero());

                } else {

                    System.out.println("Unidade: Não vinculada");
                }
            }

            System.out.println("\nTotal de vagas: " + vagas.size());

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar vagas: " + e.getMessage());
        }
    }

    // ==========================================
// CADASTRAR VEÍCULO
// ==========================================

    private static void cadastrarVeiculo(
            Scanner leitura,
            VeiculoDAO veiculoDAO,
            UnidadeDAO unidadeDAO,
            VagaDAO vagaDAO) {

        System.out.println("\n--- CADASTRAR VEÍCULO ---");

        try {

            Veiculo veiculo = new Veiculo();

            // Unidade obrigatória
            System.out.println("\n--- UNIDADES CADASTRADAS ---");
            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade responsável: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (unidade == null) {
                System.out.println("Unidade não encontrada!");
                return;
            }

            veiculo.setUnidade(unidade);

            // Dados do veículo
            System.out.print("Placa: ");
            veiculo.setPlaca(leitura.nextLine());

            System.out.print("Modelo: ");
            veiculo.setModelo(leitura.nextLine());

            System.out.print("Marca: ");
            veiculo.setMarca(leitura.nextLine());

            System.out.print("Cor: ");
            veiculo.setCor(leitura.nextLine());

            // Tipo do veículo
            System.out.println("\n--- TIPOS DE VEÍCULO ---");
            System.out.println("1 - CARRO");
            System.out.println("2 - MOTO");
            System.out.println("3 - BICICLETA");
            System.out.println("4 - OUTRO");

            System.out.print("Escolha o tipo: ");

            int tipo = lerOpcao(leitura);

            switch (tipo) {
                case 1:
                    veiculo.setTipo(TipoVeiculo.CARRO);
                    break;
                case 2:
                    veiculo.setTipo(TipoVeiculo.MOTO);
                    break;
                case 3:
                    veiculo.setTipo(TipoVeiculo.BICICLETA);
                    break;
                case 4:
                    veiculo.setTipo(TipoVeiculo.OUTRO);
                    break;
                default:
                    System.out.println("Tipo inválido!");
                    return;
            }

            // Vaga opcional
            System.out.println("\n--- VINCULAR VAGA ---");
            listarVagas(vagaDAO);

            System.out.print("\nID da vaga (0 para não vincular): ");
            Integer idVaga = Integer.parseInt(leitura.nextLine());

            if (idVaga != 0) {

                List<Vaga> vagas = vagaDAO.listarTodos();

                Vaga vagaSelecionada = null;

                for (Vaga vaga : vagas) {

                    if (vaga.getId().equals(idVaga)) {
                        vagaSelecionada = vaga;
                        break;
                    }
                }

                if (vagaSelecionada == null) {
                    System.out.println("Vaga não encontrada!");
                    return;
                }

                veiculo.setVaga(vagaSelecionada);
            }

            veiculoDAO.salvar(veiculo);

        } catch (NumberFormatException e) {

            System.out.println("Erro: informe um ID numérico válido.");

        } catch (RuntimeException e) {

            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    // ==========================================
// LISTAR VEÍCULOS
// ==========================================

    private static void listarVeiculos(VeiculoDAO veiculoDAO) {

        System.out.println("\n--- VEÍCULOS CADASTRADOS ---");

        try {

            List<Veiculo> veiculos = veiculoDAO.listarTodos();

            if (veiculos.isEmpty()) {
                System.out.println("Nenhum veículo cadastrado.");
                return;
            }

            for (Veiculo veiculo : veiculos) {

                System.out.println("\n------------------------------");
                System.out.println("ID: " + veiculo.getId());
                System.out.println("Placa: " + veiculo.getPlaca());
                System.out.println("Modelo: " + veiculo.getModelo());
                System.out.println("Marca: " + veiculo.getMarca());
                System.out.println("Cor: " + veiculo.getCor());
                System.out.println("Tipo: " + veiculo.getTipo());

                System.out.println("Unidade: "
                        + veiculo.getUnidade().getBloco()
                        + " - "
                        + veiculo.getUnidade().getNumero());

                if (veiculo.getVaga() != null) {

                    System.out.println("Vaga: "
                            + veiculo.getVaga().getIdentificacao());

                } else {

                    System.out.println("Vaga: Não vinculada");
                }
            }

            System.out.println("\nTotal de veículos: " + veiculos.size());

        } catch (RuntimeException e) {

            System.out.println("Erro ao listar veículos: " + e.getMessage());
        }
    }



    // ==========================================
    // MENU DE SOLICITAÇÕES DE MANUTENÇÃO
    // ==========================================

    private static void menuSolicitacoesManutencao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO,
            UsuarioDAO usuarioDAO,
            UnidadeDAO unidadeDAO,
            FuncionarioDAO funcionarioDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n========== GESTÃO DE MANUTENÇÃO ==========");
            System.out.println("1 - Cadastrar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Consultar solicitação por ID");
            System.out.println("4 - Atualizar status");
            System.out.println("5 - Atribuir funcionário responsável");
            System.out.println("6 - Excluir solicitação");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            switch (lerOpcao(leitura)) {

                case 1:
                    cadastrarSolicitacaoManutencao(
                            leitura, solicitacaoDAO, usuarioDAO, unidadeDAO);
                    break;

                case 2:
                    listarSolicitacoesManutencao(solicitacaoDAO);
                    break;

                case 3:
                    consultarSolicitacaoManutencao(leitura, solicitacaoDAO);
                    break;

                case 4:
                    atualizarStatusSolicitacao(leitura, solicitacaoDAO);
                    break;

                case 5:
                    atribuirFuncionarioSolicitacao(
                            leitura, solicitacaoDAO, funcionarioDAO);
                    break;

                case 6:
                    excluirSolicitacaoManutencao(leitura, solicitacaoDAO);
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    // ==========================================
    // CADASTRAR SOLICITAÇÃO
    // ==========================================

    private static void cadastrarSolicitacaoManutencao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO,
            UsuarioDAO usuarioDAO,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- CADASTRAR SOLICITAÇÃO DE MANUTENÇÃO ---");

        try {

            System.out.println("\n--- USUÁRIOS CADASTRADOS ---");

            for (Usuario usuario : usuarioDAO.listarTodos()) {
                System.out.println(
                        usuario.getId() + " - " + usuario.getNome());
            }

            System.out.print("\nID do usuário solicitante: ");
            Integer idUsuario = Integer.parseInt(leitura.nextLine());

            System.out.println("\n--- UNIDADES CADASTRADAS ---");
            listarUnidades(unidadeDAO);

            System.out.print(
                    "\nID da unidade (Enter para não informar): ");

            String unidadeTexto = leitura.nextLine();

            Integer idUnidade = unidadeTexto.isBlank()
                    ? null
                    : Integer.parseInt(unidadeTexto);

            System.out.print("Local do problema: ");
            String localProblema = leitura.nextLine();

            System.out.print("Título da solicitação: ");
            String titulo = leitura.nextLine();

            System.out.print("Descrição: ");
            String descricao = leitura.nextLine();

            System.out.println("\n--- PRIORIDADES ---");
            System.out.println("1 - BAIXA");
            System.out.println("2 - MEDIA");
            System.out.println("3 - ALTA");
            System.out.println("4 - URGENTE");

            System.out.print("Escolha a prioridade: ");

            PrioridadeManutencao prioridade;

            switch (lerOpcao(leitura)) {
                case 1:
                    prioridade = PrioridadeManutencao.BAIXA;
                    break;
                case 2:
                    prioridade = PrioridadeManutencao.MEDIA;
                    break;
                case 3:
                    prioridade = PrioridadeManutencao.ALTA;
                    break;
                case 4:
                    prioridade = PrioridadeManutencao.URGENTE;
                    break;
                default:
                    System.out.println("Prioridade inválida!");
                    return;
            }

            Integer id = solicitacaoDAO.salvar(
                    idUsuario,
                    idUnidade,
                    localProblema,
                    titulo,
                    descricao,
                    prioridade
            );

            System.out.println(
                    "\nSolicitação cadastrada com sucesso! ID: " + id);

        } catch (NumberFormatException e) {
            System.out.println("Informe um número válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao cadastrar solicitação: " + e.getMessage());
        }
    }


    // ==========================================
    // LISTAR SOLICITAÇÕES
    // ==========================================

    private static void listarSolicitacoesManutencao(
            SolicitacaoManutencaoDAO solicitacaoDAO) {

        System.out.println("\n--- SOLICITAÇÕES DE MANUTENÇÃO ---");

        try {

            List<SolicitacaoManutencao> solicitacoes =
                    solicitacaoDAO.listarTodos();

            if (solicitacoes.isEmpty()) {
                System.out.println("Nenhuma solicitação cadastrada.");
                return;
            }

            for (SolicitacaoManutencao s : solicitacoes) {

                System.out.println("\n----------------------------------");
                System.out.println("ID: " + s.getId());
                System.out.println("Título: " + s.getTitulo());
                System.out.println("Local: " + s.getLocalProblema());
                System.out.println("Descrição: " + s.getDescricao());
                System.out.println("Prioridade: " + s.getPrioridade());
                System.out.println("Status: " + s.getStatus());

                System.out.println(
                        "Solicitante: " +
                                s.getUsuarioSolicitante().getNome());

                if (s.getUnidade() != null) {
                    System.out.println(
                            "Unidade: " +
                                    s.getUnidade().getBloco() + " - " +
                                    s.getUnidade().getNumero());
                } else {
                    System.out.println("Unidade: Não informada");
                }

                if (s.getFuncionarioResponsavel() != null) {
                    System.out.println(
                            "Responsável: " +
                                    s.getFuncionarioResponsavel()
                                            .getUsuario().getNome());
                } else {
                    System.out.println("Responsável: Não atribuído");
                }

                System.out.println("Aberta em: " + s.getAbertaEm());
                System.out.println("Concluída em: " + s.getConcluidaEm());
            }

            System.out.println(
                    "\nTotal de solicitações: " + solicitacoes.size());

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao listar solicitações: " + e.getMessage());
        }
    }


    // ==========================================
    // CONSULTAR SOLICITAÇÃO POR ID
    // ==========================================

    private static void consultarSolicitacaoManutencao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO) {

        System.out.println("\n--- CONSULTAR SOLICITAÇÃO ---");

        try {

            System.out.print("Informe o ID da solicitação: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            SolicitacaoManutencao s = solicitacaoDAO.buscarPorId(id);

            if (s == null) {
                System.out.println("Solicitação não encontrada.");
                return;
            }

            System.out.println("\nID: " + s.getId());
            System.out.println("Título: " + s.getTitulo());
            System.out.println("Local: " + s.getLocalProblema());
            System.out.println("Descrição: " + s.getDescricao());
            System.out.println("Prioridade: " + s.getPrioridade());
            System.out.println("Status: " + s.getStatus());
            System.out.println(
                    "Solicitante: " + s.getUsuarioSolicitante().getNome());

            if (s.getUnidade() != null) {
                System.out.println(
                        "Unidade: " + s.getUnidade().getBloco()
                                + " - " + s.getUnidade().getNumero());
            }

            if (s.getFuncionarioResponsavel() != null) {
                System.out.println(
                        "Responsável: " +
                                s.getFuncionarioResponsavel()
                                        .getUsuario().getNome());
            } else {
                System.out.println("Responsável: Não atribuído");
            }

            System.out.println("Aberta em: " + s.getAbertaEm());
            System.out.println("Concluída em: " + s.getConcluidaEm());

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao consultar solicitação: " + e.getMessage());
        }
    }


    // ==========================================
    // ATUALIZAR STATUS
    // ==========================================

    private static void atualizarStatusSolicitacao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO) {

        System.out.println("\n--- ATUALIZAR STATUS DA SOLICITAÇÃO ---");

        try {

            listarSolicitacoesManutencao(solicitacaoDAO);

            System.out.print("\nID da solicitação: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            System.out.println("\n--- NOVO STATUS ---");
            System.out.println("1 - ABERTA");
            System.out.println("2 - EM_ANDAMENTO");
            System.out.println("3 - AGUARDANDO");
            System.out.println("4 - CONCLUIDA");
            System.out.println("5 - CANCELADA");

            System.out.print("Escolha o novo status: ");

            StatusSolicitacao novoStatus;

            switch (lerOpcao(leitura)) {
                case 1:
                    novoStatus = StatusSolicitacao.ABERTA;
                    break;
                case 2:
                    novoStatus = StatusSolicitacao.EM_ANDAMENTO;
                    break;
                case 3:
                    novoStatus = StatusSolicitacao.AGUARDANDO;
                    break;
                case 4:
                    novoStatus = StatusSolicitacao.CONCLUIDA;
                    break;
                case 5:
                    novoStatus = StatusSolicitacao.CANCELADA;
                    break;
                default:
                    System.out.println("Status inválido!");
                    return;
            }

            boolean atualizado =
                    solicitacaoDAO.atualizarStatus(id, novoStatus);

            if (atualizado) {
                System.out.println("Status atualizado com sucesso!");
            } else {
                System.out.println(
                        "Não foi possível atualizar. Verifique o ID e o status atual.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao atualizar status: " + e.getMessage());
        }
    }


    // ==========================================
    // ATRIBUIR FUNCIONÁRIO RESPONSÁVEL
    // ==========================================

    private static void atribuirFuncionarioSolicitacao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO,
            FuncionarioDAO funcionarioDAO) {

        System.out.println("\n--- ATRIBUIR RESPONSÁVEL ---");

        try {

            listarSolicitacoesManutencao(solicitacaoDAO);

            System.out.print("\nID da solicitação: ");
            Integer idSolicitacao =
                    Integer.parseInt(leitura.nextLine());

            System.out.println("\n--- FUNCIONÁRIOS CADASTRADOS ---");

            List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
                return;
            }

            for (Funcionario funcionario : funcionarios) {
                System.out.println(
                        funcionario.getId() + " - " +
                                funcionario.getUsuario().getNome());
            }

            System.out.print("\nID do funcionário responsável: ");
            Integer idFuncionario =
                    Integer.parseInt(leitura.nextLine());

            boolean atribuido = solicitacaoDAO.atribuirResponsavel(
                    idSolicitacao,
                    idFuncionario
            );

            if (atribuido) {
                System.out.println(
                        "Funcionário atribuído com sucesso!");
            } else {
                System.out.println(
                        "Não foi possível atribuir o funcionário. " +
                                "Verifique os IDs e o status da solicitação.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao atribuir funcionário: " + e.getMessage());
        }
    }


    // ==========================================
    // EXCLUIR SOLICITAÇÃO
    // ==========================================

    private static void excluirSolicitacaoManutencao(
            Scanner leitura,
            SolicitacaoManutencaoDAO solicitacaoDAO) {

        System.out.println("\n--- EXCLUIR SOLICITAÇÃO ---");

        try {

            listarSolicitacoesManutencao(solicitacaoDAO);

            System.out.print("\nID da solicitação: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            SolicitacaoManutencao solicitacao =
                    solicitacaoDAO.buscarPorId(id);

            if (solicitacao == null) {
                System.out.println("Solicitação não encontrada.");
                return;
            }

            System.out.println(
                    "\nSolicitação selecionada: " +
                            solicitacao.getTitulo());

            System.out.print("Confirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                boolean excluida = solicitacaoDAO.excluir(id);

                if (excluida) {
                    System.out.println(
                            "Solicitação excluída com sucesso!");
                } else {
                    System.out.println(
                            "Não foi possível excluir a solicitação.");
                }

            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID numérico válido.");

        } catch (RuntimeException e) {
            System.out.println(
                    "Erro ao excluir solicitação: " + e.getMessage());
            System.out.println(
                    "Verifique se existem serviços vinculados a ela.");
        }
    }
    // ==========================================
// MENU FINANCEIRO
// ==========================================

    private static void menuFinanceiro(
            Scanner leitura,
            TaxaCondominialDAO taxaCondominialDAO,
            PagamentoDAO pagamentoDAO,
            UnidadeDAO unidadeDAO) {

        boolean executando = true;

        while (executando) {

            System.out.println("\n========== GESTÃO FINANCEIRA ==========");
            System.out.println("1 - Listar taxas condominiais");
            System.out.println("2 - Cadastrar taxa condominial");
            System.out.println("3 - Buscar taxa por ID");
            System.out.println("4 - Atualizar taxa condominial");
            System.out.println("5 - Excluir taxa condominial");
            System.out.println("6 - Listar pagamentos");
            System.out.println("7 - Registrar pagamento");
            System.out.println("8 - Excluir pagamento");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            try {
                int opcao = Integer.parseInt(leitura.nextLine());

                switch (opcao) {

                    case 1:
                        listarTaxasCondominiais(taxaCondominialDAO);
                        break;

                    case 2:
                        cadastrarTaxaCondominial(
                                leitura,
                                taxaCondominialDAO,
                                unidadeDAO
                        );
                        break;

                    case 3:
                        buscarTaxaCondominial(
                                leitura,
                                taxaCondominialDAO
                        );
                        break;

                    case 4:
                        atualizarTaxaCondominial(
                                leitura,
                                taxaCondominialDAO
                        );
                        break;

                    case 5:
                        excluirTaxaCondominial(
                                leitura,
                                taxaCondominialDAO
                        );
                        break;

                    case 6:
                        listarPagamentos(pagamentoDAO);
                        break;

                    case 7:
                        registrarPagamento(
                                leitura,
                                pagamentoDAO,
                                taxaCondominialDAO
                        );
                        break;

                    case 8:
                        excluirPagamento(
                                leitura,
                                pagamentoDAO
                        );
                        break;

                    case 0:
                        executando = false;
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Informe uma opção numérica válida.");
            }
        }
    }

    private static void listarTaxasCondominiais(
            TaxaCondominialDAO taxaDAO) {

        System.out.println("\n--- TAXAS CONDOMINIAIS ---");

        try {
            List<TaxaCondominial> taxas = taxaDAO.listarTodos();

            if (taxas.isEmpty()) {
                System.out.println("Nenhuma taxa cadastrada.");
                return;
            }

            for (TaxaCondominial taxa : taxas) {
                System.out.println("----------------------------------");
                System.out.println("ID: " + taxa.getId());
                System.out.println("Unidade: "
                        + taxa.getUnidade().getBloco() + " - "
                        + taxa.getUnidade().getNumero());
                System.out.println("Descrição: " + taxa.getDescricao());
                System.out.println("Competência: " + taxa.getCompetencia());
                System.out.println("Valor: R$ " + taxa.getValor());
                System.out.println("Vencimento: " + taxa.getVencimento());
                System.out.println("Status: " + taxa.getStatus());
            }

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar taxas: " + e.getMessage());
        }
    }

    private static void cadastrarTaxaCondominial(
            Scanner leitura,
            TaxaCondominialDAO taxaDAO,
            UnidadeDAO unidadeDAO) {

        System.out.println("\n--- CADASTRAR TAXA CONDOMINIAL ---");

        try {
            listarUnidades(unidadeDAO);

            System.out.print("\nID da unidade: ");
            Integer idUnidade = Integer.parseInt(leitura.nextLine());

            UnidadeEntity unidade = unidadeDAO.buscarPorId(idUnidade);

            if (unidade == null) {
                System.out.println("Unidade não encontrada.");
                return;
            }

            System.out.print("Descrição da taxa: ");
            String descricao = leitura.nextLine();

            System.out.print("Competência (AAAA-MM): ");
            String competencia = leitura.nextLine();

            System.out.print("Valor: ");
            BigDecimal valor = new BigDecimal(
                    leitura.nextLine().replace(",", "."));

            System.out.print("Vencimento (AAAA-MM-DD): ");
            LocalDate vencimento = LocalDate.parse(leitura.nextLine());

            TaxaCondominial taxa = new TaxaCondominial();

            taxa.setUnidade(unidade);
            taxa.setDescricao(descricao);
            taxa.setCompetencia(competencia);
            taxa.setValor(valor);
            taxa.setVencimento(vencimento);
            taxa.setStatus(StatusTaxaCondominial.ABERTA);

            taxaDAO.salvar(taxa);

            System.out.println("Taxa cadastrada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID ou valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar taxa: " + e.getMessage());
        }
    }

    private static void buscarTaxaCondominial(
            Scanner leitura,
            TaxaCondominialDAO taxaDAO) {

        System.out.println("\n--- BUSCAR TAXA ---");

        try {
            System.out.print("ID da taxa: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            TaxaCondominial taxa = taxaDAO.buscarPorId(id);

            if (taxa == null) {
                System.out.println("Taxa não encontrada.");
                return;
            }

            System.out.println("\nID: " + taxa.getId());
            System.out.println("Unidade: "
                    + taxa.getUnidade().getBloco() + " - "
                    + taxa.getUnidade().getNumero());
            System.out.println("Descrição: " + taxa.getDescricao());
            System.out.println("Competência: " + taxa.getCompetencia());
            System.out.println("Valor: R$ " + taxa.getValor());
            System.out.println("Vencimento: " + taxa.getVencimento());
            System.out.println("Status: " + taxa.getStatus());

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar taxa: " + e.getMessage());
        }
    }

    private static void atualizarTaxaCondominial(
            Scanner leitura,
            TaxaCondominialDAO taxaDAO) {

        System.out.println("\n--- ATUALIZAR TAXA ---");

        try {
            System.out.print("ID da taxa: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            TaxaCondominial taxa = taxaDAO.buscarPorId(id);

            if (taxa == null) {
                System.out.println("Taxa não encontrada.");
                return;
            }

            System.out.println("Descrição atual: " + taxa.getDescricao());
            System.out.print("Nova descrição: ");
            taxa.setDescricao(leitura.nextLine());

            System.out.println("Competência atual: " + taxa.getCompetencia());
            System.out.print("Nova competência (AAAA-MM): ");
            taxa.setCompetencia(leitura.nextLine());

            System.out.println("Valor atual: " + taxa.getValor());
            System.out.print("Novo valor: ");
            taxa.setValor(new BigDecimal(
                    leitura.nextLine().replace(",", ".")));

            System.out.println("Vencimento atual: " + taxa.getVencimento());
            System.out.print("Novo vencimento (AAAA-MM-DD): ");
            taxa.setVencimento(LocalDate.parse(leitura.nextLine()));

            taxaDAO.atualizar(taxa);

            System.out.println("Taxa atualizada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe valores numéricos válidos.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar taxa: " + e.getMessage());
        }
    }

    private static void excluirTaxaCondominial(
            Scanner leitura,
            TaxaCondominialDAO taxaDAO) {

        System.out.println("\n--- EXCLUIR TAXA ---");

        try {
            System.out.print("ID da taxa: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            TaxaCondominial taxa = taxaDAO.buscarPorId(id);

            if (taxa == null) {
                System.out.println("Taxa não encontrada.");
                return;
            }

            System.out.println("Taxa: " + taxa.getDescricao());
            System.out.println("Valor: R$ " + taxa.getValor());

            System.out.print("Confirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                taxaDAO.excluir(id);
                System.out.println("Taxa excluída com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir taxa: " + e.getMessage());
            System.out.println("Verifique se existem pagamentos vinculados.");
        }
    }

    private static void listarPagamentos(PagamentoDAO pagamentoDAO) {

        System.out.println("\n--- PAGAMENTOS REGISTRADOS ---");

        try {
            List<Pagamento> pagamentos = pagamentoDAO.listarTodos();

            if (pagamentos.isEmpty()) {
                System.out.println("Nenhum pagamento registrado.");
                return;
            }

            for (Pagamento pagamento : pagamentos) {
                System.out.println("----------------------------------");
                System.out.println("ID: " + pagamento.getId());
                System.out.println("Taxa: "
                        + pagamento.getTaxa().getDescricao());
                System.out.println("Unidade: "
                        + pagamento.getTaxa().getUnidade().getBloco()
                        + " - "
                        + pagamento.getTaxa().getUnidade().getNumero());
                System.out.println("Valor pago: R$ "
                        + pagamento.getValorPago());
                System.out.println("Data: " + pagamento.getPagoEm());
                System.out.println("Forma: "
                        + pagamento.getFormaPagamento());
                System.out.println("Comprovante: "
                        + pagamento.getComprovante());
                System.out.println("Observações: "
                        + pagamento.getObservacoes());
            }

        } catch (RuntimeException e) {
            System.out.println("Erro ao listar pagamentos: " + e.getMessage());
        }
    }

    private static void registrarPagamento(
            Scanner leitura,
            PagamentoDAO pagamentoDAO,
            TaxaCondominialDAO taxaDAO) {

        System.out.println("\n--- REGISTRAR PAGAMENTO ---");

        try {
            listarTaxasCondominiais(taxaDAO);

            System.out.print("\nID da taxa: ");
            Integer idTaxa = Integer.parseInt(leitura.nextLine());

            TaxaCondominial taxa = taxaDAO.buscarPorId(idTaxa);

            if (taxa == null) {
                System.out.println("Taxa não encontrada.");
                return;
            }

            System.out.println("Taxa: " + taxa.getDescricao());
            System.out.println("Valor total: R$ " + taxa.getValor());

            System.out.print("Valor pago: ");
            BigDecimal valorPago = new BigDecimal(
                    leitura.nextLine().replace(",", "."));

            System.out.println("\nFormas de pagamento:");
            System.out.println("1 - PIX");
            System.out.println("2 - BOLETO");
            System.out.println("3 - DINHEIRO");
            System.out.println("4 - CARTAO");
            System.out.println("5 - TRANSFERENCIA");
            System.out.println("6 - OUTRA");

            System.out.print("Escolha: ");
            int opcaoForma = Integer.parseInt(leitura.nextLine());

            FormaPagamento formaPagamento;

            switch (opcaoForma) {
                case 1:
                    formaPagamento = FormaPagamento.PIX;
                    break;
                case 2:
                    formaPagamento = FormaPagamento.BOLETO;
                    break;
                case 3:
                    formaPagamento = FormaPagamento.DINHEIRO;
                    break;
                case 4:
                    formaPagamento = FormaPagamento.CARTAO;
                    break;
                case 5:
                    formaPagamento = FormaPagamento.TRANSFERENCIA;
                    break;
                case 6:
                    formaPagamento = FormaPagamento.OUTRA;
                    break;
                default:
                    System.out.println("Forma de pagamento inválida.");
                    return;
            }

            System.out.print("Comprovante (opcional): ");
            String comprovante = leitura.nextLine();

            System.out.print("Observações (opcional): ");
            String observacoes = leitura.nextLine();

            Pagamento pagamento = new Pagamento();

            pagamento.setTaxa(taxa);
            pagamento.setValorPago(valorPago);
            pagamento.setPagoEm(LocalDateTime.now());
            pagamento.setFormaPagamento(formaPagamento);
            pagamento.setComprovante(comprovante);
            pagamento.setObservacoes(observacoes);

            pagamentoDAO.salvar(pagamento);

            System.out.println("Pagamento registrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Informe valores numéricos válidos.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    private static void excluirPagamento(
            Scanner leitura,
            PagamentoDAO pagamentoDAO) {

        System.out.println("\n--- EXCLUIR PAGAMENTO ---");

        try {
            listarPagamentos(pagamentoDAO);

            System.out.print("\nID do pagamento: ");
            Integer id = Integer.parseInt(leitura.nextLine());

            Pagamento pagamento = pagamentoDAO.buscarPorId(id);

            if (pagamento == null) {
                System.out.println("Pagamento não encontrado.");
                return;
            }

            System.out.println("Valor: R$ " + pagamento.getValorPago());
            System.out.println("Taxa: "
                    + pagamento.getTaxa().getDescricao());

            System.out.print("Confirma a exclusão? (S/N): ");
            String confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                pagamentoDAO.excluir(id);
                System.out.println("Pagamento excluído com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Informe um ID válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir pagamento: " + e.getMessage());
        }
    }


}
