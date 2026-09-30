package main;

import java.util.List;
import java.util.Scanner;

import Login.LoginController;
import produtos.Produto;
import produtos.ProdutoDAO;
import vendas.Venda;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        LoginController loginController = new LoginController();

        // Sistema de Login
        System.out.println("Bem-vindo ao sistema StoreS!");
        System.out.print("Digite seu nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        boolean autenticado = loginController.autenticar(usuario, senha);

        if (!autenticado) {
            System.out.println("Falha no login. Encerrando o programa.");
            scanner.close();
            return;
        }

        // Menu de opções
        int opcao = 0;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar Produto Manualmente");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Editar Produto");
            System.out.println("4. Remover Produto");
            System.out.println("5. Comprar de Fornecedor");
            System.out.println("6. Realizar Venda");
            System.out.println("0. Sair");
            opcao = lerInteiro(scanner, "Escolha uma opção: ", 0);

            switch (opcao) {
                case 1:
                    // Cadastro de Produto Manualmente
                    String nomeProduto = lerTexto(scanner, "Nome do produto: ");
                    double preco = lerDouble(scanner, "Preço do produto: ", 0.01);
                    int quantidade = lerInteiro(scanner, "Quantidade do produto: ", 0);

                    Produto produto = new Produto(nomeProduto, preco, quantidade);
                    produtoDAO.cadastrarProduto(produto);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;

                case 2:
                    // Listagem de Produtos
                    System.out.println("Lista de Produtos:");
                    List<Produto> produtos = produtoDAO.listarProdutos();

                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtos) {
                            System.out.println("ID: " + p.getId() +
                                               ", Nome: " + p.getNome() +
                                               ", Preço: " + p.getPreco() +
                                               ", Quantidade: " + p.getQuantidade());
                        }
                    }
                    break;

                case 3:
                    // Edição de Produto
                    int idEditar = lerInteiro(scanner, "Digite o ID do produto a ser editado: ", 1);
                    String novoNome = lerTexto(scanner, "Novo nome do produto: ");
                    double novoPreco = lerDouble(scanner, "Novo preço do produto: ", 0.01);
                    int novaQuantidade = lerInteiro(scanner, "Nova quantidade do produto: ", 0);

                    Produto produtoEditado = new Produto(novoNome, novoPreco, novaQuantidade);
                    produtoEditado.setId(idEditar);
                    produtoDAO.editarProduto(produtoEditado);
                    break;

                case 4:
                    // Remoção de Produto
                    int idRemover = lerInteiro(scanner, "Digite o ID do produto a ser removido: ", 1);
                    produtoDAO.removerProduto(idRemover);
                    break;

                case 5:
                    // Compra de Produtos
                    String nomeProdutoCompra = lerTexto(scanner, "Nome do produto para compra: ");
                    Produto produtoParaCompra = produtoDAO.buscarProdutoPorNome(nomeProdutoCompra);

                    if (produtoParaCompra == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    int quantidadeCompra = lerInteiro(scanner, "Quantidade a ser comprada: ", 1);
                    double porcentagemLucro = lerDouble(scanner, "Porcentagem de lucro: ", 0);

                    double precoCompra = produtoParaCompra.getPreco();
                    double precoVenda = precoCompra * (1 + porcentagemLucro / 100);

                    boolean compraAtualizada = produtoDAO.atualizarEstoqueAposCompra(
                        produtoParaCompra.getId(), precoVenda, quantidadeCompra);
                    System.out.println(compraAtualizada
                        ? "Compra registrada com sucesso!"
                        : "Não foi possível registrar a compra.");
                    break;

                case 6:
                    // Venda de Produtos
                    String nomeProdutoVenda = lerTexto(scanner, "Nome do produto a ser vendido: ");
                    Produto produtoParaVenda = produtoDAO.buscarProdutoPorNome(nomeProdutoVenda);

                    if (produtoParaVenda == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    int quantidadeVenda = lerInteiro(scanner, "Quantidade a ser vendida: ", 1);

                    if (quantidadeVenda > produtoParaVenda.getQuantidade()) {
                        System.out.println("Quantidade vendida excede a quantidade em estoque.");
                        break;
                    }

                    double valorVenda = produtoParaVenda.getPreco() * quantidadeVenda;
                    Venda venda = new Venda(produtoParaVenda, quantidadeVenda, valorVenda);

                    // Atualiza o estoque após a venda
                    boolean estoqueAtualizado = produtoDAO.atualizarEstoqueAposVenda(
                        produtoParaVenda.getId(), quantidadeVenda);

                    if (!estoqueAtualizado) {
                        System.out.println("Não foi possível atualizar o estoque. Tente novamente.");
                        break;
                    }

                    System.out.println("Venda registrada com sucesso!");
                    System.out.println("Produto: " + venda.getProduto().getNome());
                    System.out.println("Quantidade Vendida: " + venda.getQuantidadeVendida());
                    System.out.println("Valor Total: " + venda.getValorTotal());
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("O valor não pode ficar vazio.");
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem, int minimo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Solicita novamente uma entrada numerica valida.
            }
            System.out.println("Digite um numero inteiro maior ou igual a " + minimo + ".");
        }
    }

    private static double lerDouble(Scanner scanner, String mensagem, double minimo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor >= minimo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Solicita novamente uma entrada numerica valida.
            }
            System.out.println("Digite um numero maior ou igual a " + minimo + ".");
        }
    }
}
