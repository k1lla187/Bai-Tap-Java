package vn.edu.eaut.lab10.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Lazy singleton for EntityManagerFactory.
 * Delay initialization until first use (after webapp is fully started).
 */
public class JPAUtil {

    private static volatile EntityManagerFactory EMF;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (EMF == null) {
            synchronized (JPAUtil.class) {
                if (EMF == null) {
                    try {
                        EMF = Persistence.createEntityManagerFactory("lab10PU");
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create EntityManagerFactory", e);
                    }
                }
            }
        }
        return EMF;
    }

    public static void shutdown() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}
