package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class UserRepository {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public User findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email AND u.password = :password",
                    User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.id DESC", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<User> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM User u WHERE u.email LIKE :kw OR u.fullName LIKE :kw ORDER BY u.id DESC",
                    User.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    public boolean existsByEmailExcludingId(String email, Integer excludeId) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.id != :id",
                    Long.class)
                    .setParameter("email", email)
                    .setParameter("id", excludeId)
                    .getSingleResult();
            return count > 0;
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
        } catch (Exception e) {
            em.getTransaction().rollback();
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
        } catch (Exception e) {
            em.getTransaction().rollback();
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
            if (u != null) em.remove(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
