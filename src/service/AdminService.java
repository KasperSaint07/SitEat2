package service;

import model.Admin;
import model.Booking;
import model.Table;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.TableRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository;
    private final TableRepository tableRepository;
    private final BookingRepository bookingRepository;

    public AdminService(AdminRepository adminRepository, TableRepository tableRepository, BookingRepository bookingRepository) {
        this.adminRepository = adminRepository;
        this.tableRepository = tableRepository;
        this.bookingRepository = bookingRepository;
    }
}
