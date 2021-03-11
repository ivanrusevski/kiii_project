package mk.ukim.finki.wp.magacin.service;

import mk.ukim.finki.wp.magacin.models.User;

public interface UserService {
    User register(String username,String password,String repeatPassword,String role);
    User login(String username,String password);
    User updateUser(String username, String password, String firstName,
            String lastName,
            String address,
            String email,
            String city,
            String country,
            String zipcode);
    User getUser(String username);
}
