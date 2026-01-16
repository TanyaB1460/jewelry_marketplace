package com.jewelry.service;
import com.jewelry.dao.UserDao;
import com.jewelry.model.User;
import com.jewelry.util.PasswordUtil;
import java.sql.SQLException;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authenticate(String email, String password) throws SQLException {
        User user = userDao.findByEmail(email);
        if (user != null && PasswordUtil.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User register(String email, String password, String username, String role) throws SQLException {
        User existingUser = userDao.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        String hashedPassword = PasswordUtil.hash(password);
        User newUser = new User(null, email, hashedPassword, username, role);
        userDao.save(newUser);
        return newUser;
    }

    public User getUserById(Integer userId) throws SQLException {
        return userDao.findById(userId);
    }
}
