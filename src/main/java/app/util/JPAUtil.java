package app.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Cria e guarda o EntityManagerFactory (caro de criar: um por aplicação).
 * As variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD sobrescrevem o persistence.xml.
 */
public final class JPAUtil {

    private static final String UNIDADE_PADRAO = "modulo32";
    private static EntityManagerFactory factory;

    private JPAUtil() {}

    public static synchronized void init(String unidade) {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(unidade, overridesDoAmbiente());
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null) {
            init(UNIDADE_PADRAO);
        }
        return factory.createEntityManager();
    }

    public static synchronized void close() {
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }

    private static Map<String, String> overridesDoAmbiente() {
        Map<String, String> props = new HashMap<>();
        putIfSet(props, "jakarta.persistence.jdbc.url", "DB_URL");
        putIfSet(props, "jakarta.persistence.jdbc.user", "DB_USER");
        putIfSet(props, "jakarta.persistence.jdbc.password", "DB_PASSWORD");
        return props;
    }

    private static void putIfSet(Map<String, String> props, String chave, String env) {
        String valor = System.getenv(env);
        if (valor != null && !valor.isBlank()) {
            props.put(chave, valor);
        }
    }
}
