package app.dao;

import app.domain.Produto;
import app.util.JPAUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDAOTest {

    private final ProdutoDAO dao = new ProdutoDAO();

    @BeforeAll
    static void iniciar() {
        JPAUtil.init("modulo32-test");
    }

    @AfterAll
    static void encerrar() {
        JPAUtil.close();
    }

    @Test
    void deveCadastrarEBuscarPorId() {
        Produto produto = dao.cadastrar(new Produto("A1", "Mouse", new BigDecimal("59.90")));

        assertNotNull(produto.getId());
        Produto encontrado = dao.buscarPorId(produto.getId()).orElseThrow();
        assertEquals("Mouse", encontrado.getNome());
    }

    @Test
    void deveAlterar() {
        Produto produto = dao.cadastrar(new Produto("A2", "Monitor", new BigDecimal("899.00")));
        produto.setNome("Monitor 27");
        dao.alterar(produto);

        assertEquals("Monitor 27", dao.buscarPorCodigo("A2").orElseThrow().getNome());
    }

    @Test
    void deveExcluir() {
        Produto produto = dao.cadastrar(new Produto("A3", "Cabo HDMI", new BigDecimal("25.00")));
        dao.excluir(produto.getId());

        assertTrue(dao.buscarPorId(produto.getId()).isEmpty());
    }
}
