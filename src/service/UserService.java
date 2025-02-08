package service;

import model.User;
import repositories.interfaces.IUserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class UserService {
    private final IUserRepository userRepository;
    private final Validator validator;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public boolean registerUser(String login, String password, String name, String surname, boolean gender) {
        User user = new User(0, login, password, name, surname, gender);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
            return false;
        }
        return userRepository.createUser(user);
    }

    public boolean isLoginTaken(String login) {
        // Используем метод findByLogin из IUserRepository
        return userRepository.findByLogin(login).isPresent();
    }
}
