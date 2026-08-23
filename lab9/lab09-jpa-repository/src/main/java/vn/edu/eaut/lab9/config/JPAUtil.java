package vn.edu.eaut.lab9.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for JPA EntityManagerFactory management.
 * Implements singleton pattern for EntityManagerFactory.
 */
public class JPAUtil {
    private static final EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("lab09PU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
