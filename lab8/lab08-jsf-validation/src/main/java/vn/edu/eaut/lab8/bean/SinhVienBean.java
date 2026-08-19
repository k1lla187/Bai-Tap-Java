package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.repository.SinhVienRepository;

import java.io.Serializable;
import java.util.List;

@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private SinhVien sinhVien = new SinhVien();
    private String keyword;
    private SinhVien selectedSinhVien;

    private final SinhVienRepository repo = new SinhVienRepository();

    public SinhVienBean() {
        // Default constructor
    }

    public String save() {
        if (sinhVien.getMaSinhVien() == null || sinhVien.getMaSinhVien().trim().isEmpty()) {
            addMessage("Mã sinh viên không được để trống", FacesMessage.SEVERITY_ERROR);
            return null;
        }

        repo.add(sinhVien);
        addMessage("Thành công: Đã lưu sinh viên " + sinhVien.getHoTen(), FacesMessage.SEVERITY_INFO);
        sinhVien = new SinhVien();
        return null;
    }

    public String update() {
        if (selectedSinhVien == null) {
            addMessage("Chưa chọn sinh viên để sửa", FacesMessage.SEVERITY_ERROR);
            return null;
        }

        repo.update(selectedSinhVien);
        addMessage("Thành công: Đã cập nhật sinh viên " + selectedSinhVien.getHoTen(), FacesMessage.SEVERITY_INFO);
        selectedSinhVien = null;
        return "sinhvien-list?faces-redirect=true";
    }

    public void delete(int id) {
        SinhVien sv = repo.findById(id);
        repo.delete(id);
        if (sv != null) {
            addMessage("Thành công: Đã xóa sinh viên " + sv.getHoTen(), FacesMessage.SEVERITY_INFO);
        }
    }

    public String edit(int id) {
        selectedSinhVien = repo.findById(id);
        if (selectedSinhVien == null) {
            addMessage("Không tìm thấy sinh viên", FacesMessage.SEVERITY_ERROR);
            return "sinhvien-list?faces-redirect=true";
        }
        return "sinhvien-edit?faces-redirect=true";
    }

    public String cancel() {
        selectedSinhVien = null;
        return "sinhvien-list?faces-redirect=true";
    }

    public String resetForm() {
        sinhVien = new SinhVien();
        return null;
    }

    public List<SinhVien> getDsSinhVien() {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public String search() {
        if (keyword != null) {
            keyword = keyword.trim();
        }
        return null;
    }

    public String viewAll() {
        keyword = "";
        return null;
    }

    private void addMessage(String summary, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, summary, null));
    }

    // Getters and Setters
    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SinhVien getSelectedSinhVien() {
        return selectedSinhVien;
    }

    public void setSelectedSinhVien(SinhVien selectedSinhVien) {
        this.selectedSinhVien = selectedSinhVien;
    }
}
