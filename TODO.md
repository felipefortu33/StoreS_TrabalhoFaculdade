# TODO StoreS

## Fase 1 - Estabilizacao (prioridade alta)

- [x] Alinhar o nome do banco usado pela aplicacao com os scripts SQL (`stores`).
- [x] Permitir configuracao da conexao por propriedades Java ou variaveis de ambiente.
- [ ] Criar um build reproduzivel com Maven ou Gradle e declarar o MySQL Connector.
- [ ] Documentar a execucao real do projeto no README.
- [ ] Validar entradas do menu, precos, quantidades, nomes e IDs.
- [ ] Fazer os metodos de persistencia retornarem sucesso ou erro em vez de apenas imprimirem mensagens.
- [ ] Corrigir compras para aumentar o estoque do produto existente, sem criar duplicatas.
- [ ] Garantir que vendas nunca reduzam o estoque abaixo de zero.
- [ ] Usar transacoes nas operacoes de compra e venda.

## Fase 2 - Dados e seguranca (prioridade alta)

- [ ] Remover credenciais de desenvolvimento dos scripts versionados.
- [ ] Armazenar senhas com hash seguro.
- [ ] Registrar compras, vendas e movimentacoes de estoque no banco.
- [ ] Aplicar o nivel de acesso `ADMIN` ou `USER` nas operacoes.
- [ ] Adicionar constraints e indices necessarios ao banco.

## Fase 3 - Qualidade (prioridade media)

- [ ] Separar menu, servicos de negocio, DAOs e validacoes.
- [ ] Usar `BigDecimal` para valores monetarios.
- [ ] Adicionar testes para login, estoque, compras e vendas.
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
