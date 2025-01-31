package service;
import model.User;
import model.Admin
import repositories.AdminRepository;
import repositories.UserRepository;
public class AuthService {
    private AdminRepository adminRepository;
    private UserRepository userRepository;
    public AuthService(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }
    public Admin loginAsAdmin(String username, String password){
        return adminRepository.getAdminByCrediantals(username, password);
    }
    public User loginAsUser(String username, String password){
        return userRepository.getUserByCrediantals(username, password);
    }

}
