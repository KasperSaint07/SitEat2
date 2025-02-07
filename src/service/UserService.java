package service;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.List;
import model.User;
import repositories.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    private final Validator validator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public boolean registerUser(String login, String password, String name, String surname,boolean gender) {
        User user = new User(0, login, password, name, surname, gender);

        // Проверяем валидацию
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
            return false;
        }

        return userRepository.addUser(user);
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
