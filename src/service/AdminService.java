package service;

import model.Admin;
import model.Booking;
import model.Table;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.TableRepository;
import service.interfaces.IAdminService;

import java.util.List;

public class AdminService implements IAdminService {
    private final AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Override
    public Admin getAdminByCredentials(String username, String password) {
        return adminRepository.getAdminByCredentials(username, password);
    }
}
