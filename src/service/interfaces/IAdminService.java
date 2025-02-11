package service.interfaces;
import java.util.List;
import service.AdminService;
import model.Admin;

public interface IAdminService {
    Admin getAdminByCredentials(String username, String password);
}
