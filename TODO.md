# TODO StoreS

## Fase 1 - Estabilizacao (prioridade alta)

- [x] Alinhar o nome do banco usado pela aplicacao com os scripts SQL (`stores`).
- [x] Permitir configuracao da conexao por propriedades Java ou variaveis de ambiente.
- [x] Remover a senha padrao do banco do codigo-fonte.
- [x] Criar um build reproduzivel com Maven e declarar o MySQL Connector.
- [x] Documentar a execucao real do projeto no README.
- [x] Validar entradas do menu, precos, quantidades, nomes e IDs.
- [x] Fazer as operacoes de estoque retornarem sucesso ou erro.
- [x] Corrigir compras para aumentar o estoque do produto existente, sem criar duplicatas.
- [x] Garantir que vendas nunca reduzam o estoque abaixo de zero.
- [x] Usar transacoes nas operacoes de compra e venda.

## Fase 2 - Dados e seguranca (prioridade alta)

- [ ] Remover credenciais de desenvolvimento dos scripts versionados.
- [x] Armazenar senhas com hash seguro.
- [x] Registrar compras, vendas e movimentacoes de estoque no banco.
- [x] Aplicar o nivel de acesso `ADMIN` ou `USER` nas operacoes.
- [ ] Adicionar constraints e indices necessarios ao banco.

## Fase 3 - Qualidade (prioridade media)

- [ ] Separar menu, servicos de negocio, DAOs e validacoes.
- [x] Usar `BigDecimal` para valores monetarios.
- [ ] Adicionar testes para login, estoque, compras e vendas.
- [x] Adicionar testes unitarios para o hash de senha.
- [ ] Padronizar tratamento de erros e mensagens para o usuario.
- [ ] Adicionar codigo/SKU, categoria e estoque minimo aos produtos.

## Fase 4 - Evolucao (prioridade baixa)

- [ ] Corrigir a documentacao para refletir a aplicacao existente.
- [ ] Criar relatorios e indicadores de estoque e vendas.
- [ ] Avaliar uma interface web ou desktop depois que o dominio estiver estavel.
- [ ] Avaliar API REST, Docker e frontend somente apos definir o novo escopo.
- [ ] Avaliar migracao para PostgreSQL apenas se houver uma necessidade real.

## Fora da prioridade atual

- Dashboard web.
- Integracoes externas.
- Aplicativo mobile.
- Microservicos.
- Migracao de arquitetura antes da estabilizacao do console.
