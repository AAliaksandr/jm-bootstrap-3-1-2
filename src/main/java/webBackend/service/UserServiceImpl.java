package webBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webBackend.dao.UserDao;
import webBackend.model.Role;
import webBackend.model.User;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByUsername(email);
    }
    
    @Override
    public User checkAuthority(String email, String password) {return userDao.checkAuthority(email, password);}

    
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    
    @Override
    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }
    
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
    
    @Override
    public void deleteUser(long id) {
            userDao.deleteUser(id);

    }
    
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User updateUserAndReturn(User user) {
       return userDao.updateUserAndReturn(user);
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:

    @Override
    public Role getRole(String roleName) {
        return userDao.getRole(roleName);
    }
}