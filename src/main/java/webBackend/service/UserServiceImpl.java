package webBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webBackend.dao.UserDao;
import webBackend.model.Role;
import webBackend.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByUsername(email);
    }

    @Transactional
    @Override
    public User checkAuthority(String email, String password) {return userDao.checkAuthority(email, password);}

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
            userDao.deleteUser(id);

    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public User updateUserAndReturn(User user) {
       return userDao.updateUserAndReturn(user);
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:


    @Override
    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

    @Override
    public Role getRole(String roleName) {
        return userDao.getRole(roleName);
    }

    public void fillRoleIntoTable(Role role) {
        userDao.fillRoleIntoTable(role);
    }
}