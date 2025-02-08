package service;

import model.Admin;
import model.User;
import repositories.AdminRepository;
import repositories.UserRepository;
import repositories.interfaces.IAdminRepository;
import repositories.interfaces.IUserRepository;

public class AuthService {
    private final IAdminRepository adminRepository;
    private final IUserRepository userRepository;

    public AuthService(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public Admin loginAsAdmin(String username, String password) {
        return adminRepository.getAdminByCredentials(username, password);
    }

    public User loginAsUser(String username, String password) {
        return userRepository.authenticate(username, password);
    }
}
