package service;

import model.Admin;
import model.User;
import repositories.AdminRepository;
import repositories.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public AuthService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public User loginAsUser(String username, String password) {
        return userRepository.getUserByCredentials(username, password);
    }

    public Admin loginAsAdmin(String username, String password) {
        return adminRepository.getAdminByCredentials(username, password);
    }
}
