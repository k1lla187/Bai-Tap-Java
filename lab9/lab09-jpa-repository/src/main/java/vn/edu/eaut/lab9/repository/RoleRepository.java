package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Role;
import java.util.List;

/**
 * Repository class for Role entity CRUD operations
 */
public class RoleRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Role> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r ORDER BY r.name", Role.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Role findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    public Role findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public void save(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(role);
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

    public void update(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(role);
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
            Role r = em.find(Role.class, id);
            if (r != null) {
                em.remove(r);
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
}
