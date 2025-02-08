package repositories.interfaces;

import model.Admin;

public interface IAdminRepository {
    Admin getAdminByCredentials(String username, String password);
}

