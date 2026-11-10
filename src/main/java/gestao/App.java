package gestao;

import java.util.Scanner;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

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
import gestao.persistence.dao.VisitanteDAO;
import gestao.persistence.entity.Visitante;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gestao.persistence.dao.VisitanteDAO;
import gestao.persistence.dao.AutorizacaoVisitanteDAO;

import gestao.persistence.entity.Visitante;
import gestao.persistence.entity.AutorizacaoVisitante;
import gestao.persistence.entity.StatusAutorizacao;


public class App {

    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);

        UnidadeDAO unidadeDAO = new UnidadeDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioUnidadeDAO usuarioUnidadeDAO = new UsuarioUnidadeDAO();
        OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        AutorizacaoVisitanteDAO autorizacaoVisitanteDAO = new AutorizacaoVisitanteDAO();

        boolean executando = true;

        try {

            while (executando) {

                System.out.println("\n========== GESTÃO CONDOMINIAL ==========");
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("1 - Gestão Administrativa");
                System.out.println("0 - Sair do sistema");

                System.out.print("\nEscolha uma opção: ");

                int opcao = lerOpcao(leitura);

                switch (opcao) {

                    case 1:
                        menuAdministrativo(
                                leitura,
                                unidadeDAO,
                                usuarioDAO,
                                usuarioUnidadeDAO,
                                ocorrenciaDAO,
                                visitanteDAO,
                                autorizacaoVisitanteDAO
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
    // MENU DE GESTÃO ADMINISTRATIVA
    // ==========================================

    private static void menuAdministrativo(
            Scanner leitura,
            UnidadeDAO unidadeDAO,
            UsuarioDAO usuarioDAO,
            UsuarioUnidadeDAO usuarioUnidadeDAO,
            OcorrenciaDAO ocorrenciaDAO,
            VisitanteDAO visitanteDAO,
            AutorizacaoVisitanteDAO autorizacaoVisitanteDAO) {

        boolean voltar = false;

        while (!voltar) {

            System.out.println("\n========== GESTÃO ADMINISTRATIVA ==========");
            System.out.println("1 - Gestão de Unidades");
            System.out.println("2 - Gestão de Usuários");
            System.out.println("3 - Gestão de Vínculos");
            System.out.println("4 - Gestão de Ocorrências");
            System.out.println("5 - Gestão de Visitantes");
            System.out.println("6 - Gestão de Autorizações de Visitantes");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");

            int opcao = lerOpcao(leitura);

            switch (opcao) {

                case 1:
                    menuUnidades(leitura, unidadeDAO);
                    break;

                case 2:
                    menuUsuarios(leitura, usuarioDAO);
                    break;

                case 3:
                    menuVinculos(
                            leitura,
                            unidadeDAO,
                            usuarioDAO,
                            usuarioUnidadeDAO
                    );
                    break;
                case 4:
                    menuOcorrencias(
                            leitura,
                            unidadeDAO,
                            usuarioDAO,
                            ocorrenciaDAO
                    );
                    break;
                case 5:
                    menuVisitantes(leitura, visitanteDAO);
                    break;
                case 6:
                    menuAutorizacoes(
                            leitura,
                            visitanteDAO,
                            unidadeDAO,
                            usuarioDAO,
                            autorizacaoVisitanteDAO
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
    // LEITURA DAS OPÇÕES DOS MENUS
    // ==========================================

    private static int lerOpcao(Scanner leitura) {

        try {

            return Integer.parseInt(leitura.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }
}
