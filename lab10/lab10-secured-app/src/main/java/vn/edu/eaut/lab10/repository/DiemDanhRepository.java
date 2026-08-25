package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.DiemDanh;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

public class DiemDanhRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public DiemDanh findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(DiemDanh.class, id); }
        finally { em.close(); }
    }

    public List<DiemDanh> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DiemDanh d ORDER BY d.ngayDiemDanh DESC, d.id DESC", DiemDanh.class).getResultList();
        } finally { em.close(); }
    }

    public List<DiemDanh> findByNgay(LocalDate ngay) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DiemDanh d WHERE d.ngayDiemDanh = :ngay ORDER BY d.id DESC", DiemDanh.class)
                    .setParameter("ngay", ngay).getResultList();
        } finally { em.close(); }
    }

    public List<DiemDanh> findBySinhVienId(Integer sinhVienId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DiemDanh d WHERE d.sinhVien.id = :id ORDER BY d.ngayDiemDanh DESC", DiemDanh.class)
                    .setParameter("id", sinhVienId).getResultList();
        } finally { em.close(); }
    }

    public List<DiemDanh> findByMonHocId(Integer monHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DiemDanh d WHERE d.monHoc.id = :id ORDER BY d.ngayDiemDanh DESC", DiemDanh.class)
                    .setParameter("id", monHocId).getResultList();
        } finally { em.close(); }
    }

    public List<DiemDanh> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT d FROM DiemDanh d WHERE d.sinhVien.hoTen LIKE :k OR d.sinhVien.maSV LIKE :k OR d.monHoc.tenMon LIKE :k ORDER BY d.ngayDiemDanh DESC",
                    DiemDanh.class).setParameter("k", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(d) FROM DiemDanh d", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    public long countByTrangThai(DiemDanh.TrangThaiDiemDanh trangThai) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM DiemDanh d WHERE d.trangThai = :tt", Long.class)
                    .setParameter("tt", trangThai).getSingleResult();
        } finally { em.close(); }
    }

    public void save(DiemDanh dd) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(dd); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(DiemDanh dd) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(dd); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DiemDanh d = em.find(DiemDanh.class, id);
            if (d != null) em.remove(d);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
