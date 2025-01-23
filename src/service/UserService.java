package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users = new ArrayList<>();


    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Если пользователь не найден
    }

    public void registerUser(String username, String password) {
        int newId = users.size() + 1;
        users.add(new User(newId, username, password));
    }
}