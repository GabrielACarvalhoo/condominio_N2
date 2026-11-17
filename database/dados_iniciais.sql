-- ============================================================
-- DADOS INICIAIS DE DEMONSTRACAO
-- Sistema de Gestao de Condominio
-- Cenario ficticio: Residencial Jardim das Palmeiras
--
-- ATENCAO: este script APAGA os dados atuais das tabelas
-- operacionais, preservando estrutura, perfil e cargo.
-- Execute somente apos confirmar o backup.
-- Todos os dados pessoais sao ficticios.
-- senha_hash e um placeholder demonstrativo, nao autentica.
-- ============================================================

USE gestao_condominio;
START TRANSACTION;

-- Limpeza em ordem inversa das dependencias (FKs)
DELETE FROM pagamento;
DELETE FROM taxa_condominial;
DELETE FROM despesa;
DELETE FROM documento;
DELETE FROM comunicado;
DELETE FROM servico_manutencao;
DELETE FROM solicitacao_manutencao;
DELETE FROM veiculo;
DELETE FROM vaga;
DELETE FROM reserva;
DELETE FROM espaco_comum;
DELETE FROM ocorrencia;
DELETE FROM encomenda;
DELETE FROM registro_acesso;
DELETE FROM autorizacao_visitante;
DELETE FROM visitante;
DELETE FROM funcionario;
DELETE FROM usuario_unidade;
DELETE FROM usuario;
DELETE FROM unidade;

-- Unidades
INSERT INTO unidade (id_unidade, bloco, numero, andar, metragem, observacoes) VALUES
(1,'A','101',1,68.50,'Apartamento de dois quartos'),
(2,'A','102',1,72.00,'Apartamento de dois quartos'),
(3,'A','201',2,68.50,'Apartamento de dois quartos'),
(4,'A','202',2,85.00,'Apartamento de tres quartos'),
(5,'B','101',1,68.50,'Apartamento de dois quartos'),
(6,'B','102',1,72.00,'Apartamento de dois quartos'),
(7,'B','201',2,85.00,'Apartamento de tres quartos'),
(8,'B','202',2,90.00,'Cobertura duplex');

-- Usuarios: os perfis 1 a 8 ja existem em perfil
INSERT INTO usuario (id_usuario,id_perfil,nome,cpf,email,telefone,senha_hash,ativo,criado_em) VALUES
(1,1,'Mariana Alves Costa','90000000001','mariana.costa@exemplo.com','(61) 90000-0001','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-10 09:00:00'),
(2,2,'Rafael Mendes Lima','90000000002','rafael.lima@exemplo.com','(61) 90000-0002','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-12 10:00:00'),
(3,1,'Camila Ribeiro Souza','90000000003','camila.souza@exemplo.com','(61) 90000-0003','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-02-01 11:00:00'),
(4,2,'Bruno Ferreira Rocha','90000000004','bruno.rocha@exemplo.com','(61) 90000-0004','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-02-05 14:00:00'),
(5,1,'Juliana Martins Oliveira','90000000005','juliana.oliveira@exemplo.com','(61) 90000-0005','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-03-03 08:30:00'),
(6,1,'Eduardo Nascimento Reis','90000000006','eduardo.reis@exemplo.com','(61) 90000-0006','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-03-10 09:30:00'),
(7,2,'Patricia Gomes Barros','90000000007','patricia.barros@exemplo.com','(61) 90000-0007','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-04-02 13:00:00'),
(8,1,'Lucas Cardoso Pinto','90000000008','lucas.pinto@exemplo.com','(61) 90000-0008','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-04-15 15:00:00'),
(9,3,'Roberto Almeida Freitas','90000000009','roberto.freitas@exemplo.com','(61) 90000-0009','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-05 08:00:00'),
(10,4,'Fernanda Castro Melo','90000000010','fernanda.melo@exemplo.com','(61) 90000-0010','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-06 08:00:00'),
(11,5,'Anderson Silva Nunes','90000000011','anderson.nunes@exemplo.com','(61) 90000-0011','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-08 07:00:00'),
(12,6,'Carlos Henrique Dias','90000000012','carlos.dias@exemplo.com','(61) 90000-0012','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-08 07:10:00'),
(13,7,'Aline Pereira Lopes','90000000013','aline.lopes@exemplo.com','(61) 90000-0013','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-08 07:20:00'),
(14,8,'Marcos Vinicius Tavares','90000000014','marcos.tavares@exemplo.com','(61) 90000-0014','HASH_DEMO_NAO_UTILIZAR_LOGIN',TRUE,'2026-01-08 07:30:00');

INSERT INTO usuario_unidade (id_usuario_unidade,id_usuario,id_unidade,tipo_vinculo,data_inicio,data_fim,responsavel_financeiro) VALUES
(1,1,1,'PROPRIETARIO','2024-02-01',NULL,TRUE),(2,2,2,'INQUILINO','2025-03-01',NULL,TRUE),
(3,3,3,'PROPRIETARIO','2023-08-15',NULL,TRUE),(4,4,4,'INQUILINO','2025-07-01',NULL,TRUE),
(5,5,5,'PROPRIETARIO','2022-11-10',NULL,TRUE),(6,6,6,'PROPRIETARIO','2024-05-20',NULL,TRUE),
(7,7,7,'INQUILINO','2026-01-01',NULL,TRUE),(8,8,8,'PROPRIETARIO','2021-09-05',NULL,TRUE),
(9,9,1,'MORADOR','2026-01-05',NULL,FALSE);

-- Cargos 1 a 4 ja existem em cargo
INSERT INTO funcionario (id_funcionario,id_usuario,id_cargo,data_admissao,data_demissao) VALUES
(1,11,1,'2023-04-10',NULL),(2,12,2,'2022-08-01',NULL),
(3,13,3,'2024-02-15',NULL),(4,14,4,'2023-11-20',NULL);

-- Portaria
INSERT INTO visitante (id_visitante,nome,documento,telefone,observacoes) VALUES
(1,'Daniel Martins','DOC-DEMO-001','(61) 91111-0001','Visitante da unidade A-101'),
(2,'Sofia Carvalho','DOC-DEMO-002','(61) 91111-0002','Familiar de morador da unidade A-201'),
(3,'Thiago Ribeiro','DOC-DEMO-003','(61) 91111-0003','Prestador de servico autorizado'),
(4,'Beatriz Nogueira','DOC-DEMO-004','(61) 91111-0004','Convidada da unidade B-202');

INSERT INTO autorizacao_visitante (id_autorizacao,id_visitante,id_unidade,id_usuario_autorizador,inicio_validade,fim_validade,quantidade_acessos,status,observacoes) VALUES
(1,1,1,1,'2026-11-17 08:00:00','2026-11-17 20:00:00',2,'APROVADA','Visita familiar'),
(2,2,3,3,'2026-11-16 09:00:00','2026-11-16 18:00:00',1,'EXPIRADA','Visita realizada'),
(3,3,6,6,'2026-11-18 08:00:00','2026-11-18 17:00:00',1,'APROVADA','Manutencao autorizada'),
(4,4,8,8,'2026-11-20 14:00:00','2026-11-20 23:00:00',2,'PENDENTE','Convidada para confraternizacao');

INSERT INTO registro_acesso (id_acesso,id_autorizacao,id_funcionario_portaria,entrada,saida,observacoes) VALUES
(1,2,1,'2026-11-16 10:12:00','2026-11-16 11:35:00','Entrada e saida registradas'),
(2,1,1,'2026-11-17 09:05:00',NULL,'Visitante permanece no condominio');

INSERT INTO encomenda (id_encomenda,id_unidade,id_funcionario_recebimento,descricao,transportadora,codigo_rastreio,recebida_em,retirada_em,retirado_por,status,observacoes) VALUES
(1,1,1,'Caixa pequena - livros','Correios','BR-DEMO-000001','2026-11-16 13:20:00','2026-11-16 18:10:00','Mariana Alves Costa','RETIRADA','Retirada mediante identificacao'),
(2,2,1,'Pacote de utensilios domesticos','Loggi','LG-DEMO-000002','2026-11-17 09:40:00',NULL,NULL,'RECEBIDA','Aguardando retirada'),
(3,5,1,'Envelope de correspondencia','Correios','BR-DEMO-000003','2026-11-17 10:15:00',NULL,NULL,'RECEBIDA','Correspondencia registrada');

-- Ocorrencias
INSERT INTO ocorrencia (id_ocorrencia,id_unidade,id_usuario_registro,id_funcionario_responsavel,categoria,titulo,descricao,registrada_em,status) VALUES
(1,2,2,2,'INFRAESTRUTURA','Vazamento na area de servico','Pequeno vazamento na conexao da torneira da area de servico.','2026-11-15 08:30:00','EM_ANALISE'),
(2,4,4,2,'CONVIVENCIA','Barulho fora do horario','Relato de barulho elevado no periodo noturno; administracao orientou os envolvidos.','2026-11-14 22:15:00','RESOLVIDA'),
(3,7,7,NULL,'PORTARIA','Falha no interfone','Interfone da unidade apresenta falhas intermitentes.','2026-11-17 07:50:00','ABERTA');

-- Areas comuns e reservas
INSERT INTO espaco_comum (id_espaco,nome,descricao,capacidade,regras,ativo) VALUES
(1,'Salao de Festas','Espaco para eventos e confraternizacoes.',60,'Reserva antecipada; limpeza apos uso; respeitar horario.',TRUE),
(2,'Churrasqueira','Area externa com churrasqueira e mesas.',25,'Uso mediante reserva; entregar limpo.',TRUE),
(3,'Sala de Reunioes','Ambiente para reunioes de moradores e administracao.',12,'Uso prioritario para assuntos condominiais.',TRUE);

INSERT INTO reserva (id_reserva,id_espaco,id_usuario_solicitante,inicio,fim,quantidade_pessoas,status,id_usuario_aprovador,solicitada_em,observacoes) VALUES
(1,1,8,'2026-11-22 16:00:00','2026-11-22 22:00:00',35,'APROVADA',9,'2026-11-10 10:00:00','Aniversario familiar'),
(2,2,1,'2026-11-29 11:00:00','2026-11-29 16:00:00',18,'PENDENTE',NULL,'2026-11-16 14:30:00','Almoco de confraternizacao'),
(3,3,3,'2026-11-12 19:00:00','2026-11-12 20:30:00',10,'CONCLUIDA',9,'2026-11-05 09:00:00','Reuniao de moradores');

-- Garagem
INSERT INTO vaga (id_vaga,id_unidade,identificacao,tipo,coberta,status) VALUES
(1,1,'A-01','CARRO',TRUE,'VINCULADA'),(2,2,'A-02','CARRO',TRUE,'VINCULADA'),
(3,3,'A-03','MOTO',FALSE,'VINCULADA'),(4,4,'A-04','CARRO',TRUE,'VINCULADA'),
(5,5,'B-01','CARRO',TRUE,'VINCULADA'),(6,6,'B-02','CARRO',FALSE,'VINCULADA'),
(7,7,'B-03','CARRO',TRUE,'MANUTENCAO'),(8,8,'B-04','CARRO',TRUE,'VINCULADA'),
(9,NULL,'VIS-01','CARRO',FALSE,'DISPONIVEL');

INSERT INTO veiculo (id_veiculo,id_unidade,id_usuario,placa,modelo,marca,cor,tipo,id_vaga) VALUES
(1,1,1,'DEMO1A01','Onix','Chevrolet','Prata','CARRO',1),
(2,2,2,'DEMO2B02','HB20','Hyundai','Branco','CARRO',2),
(3,3,3,'DEMO3C03','CG 160','Honda','Vermelha','MOTO',3),
(4,5,5,'DEMO5E05','Argo','Fiat','Cinza','CARRO',5),
(5,8,8,'DEMO8H08','Corolla','Toyota','Preto','CARRO',8);

-- Manutencao
INSERT INTO solicitacao_manutencao (id_solicitacao,id_usuario_solicitante,id_unidade,id_funcionario_responsavel,local_problema,titulo,descricao,prioridade,status,aberta_em,concluida_em) VALUES
(1,2,2,4,'Area de servico - A-102','Reparo de vazamento','Verificar conexao da torneira e substituir vedacao, se necessario.','ALTA','EM_ANDAMENTO','2026-11-15 08:45:00',NULL),
(2,7,7,4,'Unidade B-201','Verificacao do interfone','Avaliar falha intermitente no interfone da unidade.','MEDIA','ABERTA','2026-11-17 08:00:00',NULL),
(3,5,5,4,'Garagem - vaga B-01','Ajuste de iluminacao','Substituir lampada queimada proxima a vaga.','BAIXA','CONCLUIDA','2026-11-10 09:00:00','2026-11-11 15:00:00');

INSERT INTO servico_manutencao (id_servico,id_solicitacao,id_funcionario_executor,descricao,realizado_em,custo,observacoes) VALUES
(1,3,4,'Substituicao da lampada e teste do ponto de iluminacao.','2026-11-11 14:30:00',38.50,'Servico concluido e testado');

-- Comunicacao e documentos (caminhos sao exemplos; arquivos nao sao criados)
INSERT INTO comunicado (id_comunicado,id_usuario_autor,titulo,conteudo,publicado_em,inicio_exibicao,fim_exibicao,status) VALUES
(1,9,'Limpeza da caixa d agua','A limpeza dos reservatorios sera realizada em 25/11, das 08h as 12h. Podera ocorrer interrupcao temporaria no abastecimento.','2026-11-16 10:00:00','2026-11-16 10:00:00','2026-11-25 23:59:00','PUBLICADO'),
(2,10,'Assembleia de moradores','Assembleia no dia 30/11, as 19h, na sala de reunioes.','2026-11-17 09:00:00','2026-11-17 09:00:00','2026-11-30 23:59:00','PUBLICADO'),
(3,9,'Orcamento anual - rascunho','Documento em preparacao para avaliacao da administracao.','2026-11-17 09:30:00',NULL,NULL,'RASCUNHO');

INSERT INTO documento (id_documento,id_usuario_responsavel,titulo,descricao,categoria,caminho_arquivo,enviado_em,visibilidade) VALUES
(1,9,'Regimento interno','Versao vigente do regimento interno.','Administrativo','documentos/regimento-interno-demo.pdf','2026-02-01 10:00:00','TODOS'),
(2,9,'Ata da assembleia - outubro','Registro da assembleia ordinaria de outubro de 2026.','Atas','documentos/ata-assembleia-outubro-demo.pdf','2026-10-20 15:00:00','MORADORES'),
(3,10,'Contratos de manutencao','Documentacao de contratos e prestadores de servico.','Contratos','documentos/contratos-manutencao-demo.pdf','2026-11-01 11:00:00','ADMINISTRACAO');

-- Financeiro: status coerentes com valores pagos e vencimentos
INSERT INTO taxa_condominial (id_taxa,id_unidade,descricao,competencia,valor,vencimento,status) VALUES
(1,1,'Taxa condominial','2026-11',450.00,'2026-11-10','PAGA'),
(2,2,'Taxa condominial','2026-11',450.00,'2026-11-10','PARCIAL'),
(3,3,'Taxa condominial','2026-11',450.00,'2026-11-10','VENCIDA'),
(4,4,'Taxa condominial','2026-11',450.00,'2026-11-10','VENCIDA'),
(5,5,'Taxa condominial','2026-11',450.00,'2026-11-10','PAGA'),
(6,6,'Taxa condominial','2026-11',450.00,'2026-11-10','ABERTA'),
(7,7,'Taxa condominial','2026-11',450.00,'2026-11-10','ABERTA'),
(8,8,'Taxa condominial','2026-11',520.00,'2026-11-10','ABERTA');

INSERT INTO pagamento (id_pagamento,id_taxa,valor_pago,pago_em,forma_pagamento,comprovante,observacoes) VALUES
(1,1,450.00,'2026-11-08 10:15:00','PIX','comprovantes/pagamento-demo-001.pdf','Pagamento integral da competencia 2026-11'),
(2,2,200.00,'2026-11-09 14:20:00','PIX','comprovantes/pagamento-demo-002.pdf','Pagamento parcial; saldo pendente de R$ 250,00'),
(3,5,450.00,'2026-11-07 09:40:00','BOLETO','comprovantes/pagamento-demo-003.pdf','Pagamento integral da competencia 2026-11');

INSERT INTO despesa (id_despesa,id_usuario_registro,descricao,categoria,fornecedor,valor,data_despesa,vencimento,paga,paga_em,observacoes) VALUES
(1,9,'Servico de limpeza das areas comuns','Limpeza','Brilho Servicos Gerais',1250.00,'2026-11-05','2026-11-10',TRUE,'2026-11-09','Pagamento conforme contrato mensal'),
(2,9,'Manutencao preventiva dos portoes','Manutencao','Portas e Motores Ltda.',680.00,'2026-11-12','2026-11-25',FALSE,NULL,'Aguardando vencimento'),
(3,10,'Compra de lampadas para garagem','Material','Comercial Eletrica Central',185.90,'2026-11-11','2026-11-18',FALSE,NULL,'Material para areas comuns'),
(4,9,'Consumo de agua das areas comuns','Utilidades','Companhia de Saneamento',430.75,'2026-11-01','2026-11-15',TRUE,'2026-11-14','Conta mensal das areas comuns');

COMMIT;

-- Conferencia dos totais apos a importacao
SELECT 'perfil' AS tabela, COUNT(*) AS total FROM perfil
UNION ALL SELECT 'cargo', COUNT(*) FROM cargo
UNION ALL SELECT 'usuario', COUNT(*) FROM usuario
UNION ALL SELECT 'unidade', COUNT(*) FROM unidade
UNION ALL SELECT 'usuario_unidade', COUNT(*) FROM usuario_unidade
UNION ALL SELECT 'funcionario', COUNT(*) FROM funcionario
UNION ALL SELECT 'visitante', COUNT(*) FROM visitante
UNION ALL SELECT 'autorizacao_visitante', COUNT(*) FROM autorizacao_visitante
UNION ALL SELECT 'registro_acesso', COUNT(*) FROM registro_acesso
UNION ALL SELECT 'encomenda', COUNT(*) FROM encomenda
UNION ALL SELECT 'ocorrencia', COUNT(*) FROM ocorrencia
UNION ALL SELECT 'espaco_comum', COUNT(*) FROM espaco_comum
UNION ALL SELECT 'reserva', COUNT(*) FROM reserva
UNION ALL SELECT 'vaga', COUNT(*) FROM vaga
UNION ALL SELECT 'veiculo', COUNT(*) FROM veiculo
UNION ALL SELECT 'solicitacao_manutencao', COUNT(*) FROM solicitacao_manutencao
UNION ALL SELECT 'servico_manutencao', COUNT(*) FROM servico_manutencao
UNION ALL SELECT 'comunicado', COUNT(*) FROM comunicado
UNION ALL SELECT 'documento', COUNT(*) FROM documento
UNION ALL SELECT 'taxa_condominial', COUNT(*) FROM taxa_condominial
UNION ALL SELECT 'pagamento', COUNT(*) FROM pagamento
UNION ALL SELECT 'despesa', COUNT(*) FROM despesa;
