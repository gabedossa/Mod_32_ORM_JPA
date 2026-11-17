package app.dao;

import app.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/** CRUD básico reutilizável para qualquer entidade. */
public abstract class GenericDAO<T, ID> {

    private final Class<T> tipo;

    protected GenericDAO(Class<T> tipo) {
        this.tipo = tipo;
    }

    public T cadastrar(T entidade) {
        emTransacao(em -> em.persist(entidade));
        return entidade;
    }

    public T alterar(T entidade) {
        return comTransacao(em -> em.merge(entidade));
    }

    public void excluir(ID id) {
        emTransacao(em -> {
            T encontrado = em.find(tipo, id);
            if (encontrado != null) {
                em.remove(encontrado);
            }
        });
    }

    public Optional<T> buscarPorId(ID id) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(tipo, id));
        }
    }

    public List<T> buscarTodos() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT e FROM " + tipo.getSimpleName() + " e", tipo).getResultList();
        }
    }

    protected void emTransacao(Consumer<EntityManager> acao) {
        comTransacao(em -> {
            acao.accept(em);
            return null;
        });
    }

    protected <R> R comTransacao(Function<EntityManager, R> acao) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                R resultado = acao.apply(em);
                tx.commit();
                return resultado;
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }
}
