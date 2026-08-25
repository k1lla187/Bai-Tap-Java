package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class MonHocRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public MonHoc findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(MonHoc.class, id); }
        finally { em.close(); }
    }

    public MonHoc findByMaMon(String maMon) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MonHoc m WHERE m.maMon = :ma", MonHoc.class)
                    .setParameter("ma", maMon).getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    public List<MonHoc> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MonHoc m ORDER BY m.id DESC", MonHoc.class).getResultList();
        } finally { em.close(); }
    }

    public List<MonHoc> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT m FROM MonHoc m WHERE m.maMon LIKE :k OR m.tenMon LIKE :k ORDER BY m.id DESC",
                    MonHoc.class).setParameter("k", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }

    public boolean existsByMaMon(String maMon) { return findByMaMon(maMon) != null; }

    public void save(MonHoc mh) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(mh); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(MonHoc mh) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(mh); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            MonHoc m = em.find(MonHoc.class, id);
            if (m != null) em.remove(m);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(m) FROM MonHoc m", Long.class).getSingleResult(); }
        finally { em.close(); }
    }
}
