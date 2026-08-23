package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.MonHoc;
import java.util.List;

/**
 * Repository class for MonHoc entity CRUD operations
 */
public class MonHocRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<MonHoc> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MonHoc m ORDER BY m.tenMon", MonHoc.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public MonHoc findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(MonHoc.class, id);
        } finally {
            em.close();
        }
    }

    public MonHoc findByMaMon(String maMon) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MonHoc m WHERE m.maMon = :maMon", MonHoc.class)
                    .setParameter("maMon", maMon)
                    .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public void save(MonHoc monHoc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(monHoc);
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

    public void update(MonHoc monHoc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(monHoc);
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
            MonHoc mh = em.find(MonHoc.class, id);
            if (mh != null) {
                em.remove(mh);
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

    public List<MonHoc> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT m FROM MonHoc m WHERE LOWER(m.tenMon) LIKE :kw OR LOWER(m.maMon) LIKE :kw";
            return em.createQuery(jpql, MonHoc.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
