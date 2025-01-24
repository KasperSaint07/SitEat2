package repositories.interfaces;

import model.User;
import java.util.List;

public interface IUserRepository {
    boolean createUser(User user); // Create a new user in the database.
    User getUserById(int id); // Retrieve a user by their ID.
    List<User> getAllUsers(); // Retrieve all users from the database.
    boolean updateUser(User user); // Update the details of an existing user.
    boolean deleteUser(int id); // Delete a user by their ID.
    User authenticate(String login, String password); // Authenticate a user by their login and password.
}