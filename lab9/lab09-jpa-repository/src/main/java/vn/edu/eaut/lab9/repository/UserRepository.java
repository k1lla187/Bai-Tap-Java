package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.User;
import java.util.List;

/**
 * Repository class for User entity CRUD operations
 */
public class UserRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.id DESC", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public User findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public User findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.isActive = true", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User u = em.find(User.class, id);
            if (u != null) {
                em.remove(u);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }
}
