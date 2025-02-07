package repositories.interfaces;

import model.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    boolean createUser(User user); // Создать нового пользователя
    User getUserById(int id); // Получить пользователя по ID
    List<User> getAllUsers(); // Получить всех пользователей
    boolean updateUser(User user); // Обновить данные пользователя
    boolean deleteUser(int id); // Удалить пользователя по ID
    User authenticate(String login, String password); // Аутентификация пользователя
    Optional<User> findByLogin(String login); // Поиск пользователя по логину (StackOverFlow посмотрите скину)
}
