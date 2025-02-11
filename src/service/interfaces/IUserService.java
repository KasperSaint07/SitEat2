package service.interfaces;
import java.util.List;

import model.User;
import service.UserService;

public interface IUserService {
    boolean registerUser(String login, String password, String name, String surname, boolean gender);
    boolean isLoginTaken(String login);
}
