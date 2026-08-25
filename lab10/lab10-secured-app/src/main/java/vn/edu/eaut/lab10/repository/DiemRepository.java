package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.Diem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class DiemRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public Diem findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(Diem.class, id); }
        finally { em.close(); }
    }

    public List<Diem> findBySinhVienId(Integer sinhVienId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d WHERE d.sinhVien.id = :sid ORDER BY d.id DESC", Diem.class)
                    .setParameter("sid", sinhVienId).getResultList();
        } finally { em.close(); }
    }

    public List<Diem> findByMonHocId(Integer monHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d WHERE d.monHoc.id = :mid ORDER BY d.id DESC", Diem.class)
                    .setParameter("mid", monHocId).getResultList();
        } finally { em.close(); }
    }

    public List<Diem> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d ORDER BY d.id DESC", Diem.class).getResultList();
        } finally { em.close(); }
    }

    public Diem findBySinhVienAndMonHoc(Integer sinhVienId, Integer monHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT d FROM Diem d WHERE d.sinhVien.id = :sid AND d.monHoc.id = :mid",
                    Diem.class).setParameter("sid", sinhVienId).setParameter("mid", monHocId)
                    .getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    public void save(Diem d) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(d); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(Diem d) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(d); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Diem d = em.find(Diem.class, id);
            if (d != null) em.remove(d);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(d) FROM Diem d", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    public long countByXepLoai(String xepLoai) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM Diem d WHERE d.xepLoai = :xl", Long.class)
                    .setParameter("xl", xepLoai).getSingleResult();
        } finally { em.close(); }
    }
}
