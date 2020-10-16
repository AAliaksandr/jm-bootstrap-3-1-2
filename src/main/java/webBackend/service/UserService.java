package webBackend.service;

import webBackend.model.Role;
import webBackend.model.User;
import java.util.List;

public interface UserService {
    User getUserById(long id);
    User getUserByEmail(String email);
    User checkAuthority(String email, String password);
    List<User> getAllUsers();
    void deleteAllUsers();
    void addUser(User user);
    void deleteUser(long id);
    void updateUser(User user);
    User updateUserAndReturn(User user);
    List<Role> getAllRoles();
    Role getRole(String roleName);
}
