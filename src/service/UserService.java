package service;

import model.User;
import repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(String login, String password, String name, String surname,boolean gender) {
        return userRepository.addUser(new User(0, login,password,name,surname,gender));
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
