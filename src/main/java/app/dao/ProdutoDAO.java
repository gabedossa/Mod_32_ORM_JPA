package app.dao;

import app.domain.Produto;
import app.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class ProdutoDAO extends GenericDAO<Produto, Long> {

    public ProdutoDAO() {
        super(Produto.class);
    }

    public Optional<Produto> buscarPorCodigo(String codigo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT p FROM Produto p WHERE p.codigo = :codigo", Produto.class)
                    .setParameter("codigo", codigo)
                    .getResultStream()
                    .findFirst();
        }
    }
}
