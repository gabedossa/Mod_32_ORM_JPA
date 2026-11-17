package app;

import app.dao.ProdutoDAO;
import app.domain.Produto;
import app.util.JPAUtil;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        try {
            ProdutoDAO dao = new ProdutoDAO();

            String codigo = "P" + System.currentTimeMillis() % 1_000_000;
            Produto produto = dao.cadastrar(new Produto(codigo, "Teclado", new BigDecimal("199.90")));
            System.out.println("Cadastrado: " + produto);

            produto.setPreco(new BigDecimal("179.90"));
            dao.alterar(produto);

            System.out.println("Produtos no banco:");
            dao.buscarTodos().forEach(p -> System.out.println("  " + p));
        } finally {
            JPAUtil.close();
        }
    }
}
