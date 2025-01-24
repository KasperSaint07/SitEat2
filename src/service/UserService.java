package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();


    public User authenticate(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Если пользователь не найден
    }

    public void registerUser(String login, String password) {
        int newId = users.size() + 1;
        users.add(new User(newId, login, password));
    }
}