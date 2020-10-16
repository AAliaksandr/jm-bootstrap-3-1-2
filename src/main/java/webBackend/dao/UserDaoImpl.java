package webBackend.dao;

import org.springframework.stereotype.Repository;
import webBackend.model.Role;
import webBackend.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entitymanager;

    @Override
    public User getUserById(long id) {
        return entitymanager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String email) {
        TypedQuery<User> query = entitymanager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public User checkAuthority(String email, String password) {
        TypedQuery<User> query = entitymanager.createQuery("SELECT DISTINCT u FROM User u WHERE u.email = :email and u.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password);
        return query.getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entitymanager.createQuery("SELECT DISTINCT u FROM User u ORDER BY u.id ", User.class)
                .setHint( "org.hibernate.readOnly", true );
        return query.getResultList();
    }

    @Override
    public void deleteAllUsers() {
        entitymanager.createQuery("DELETE FROM User").executeUpdate();
    }

    @Override
    public void addUser(User user) {
        user.setRoles(user.getRoles()
                .stream()
                .map(x-> getRole(x.getRole()))
                .collect(Collectors.toList()));
        entitymanager.persist(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = entitymanager.find(User.class, id);
        entitymanager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        user.setRoles(user.getRoles()
                .stream()
                .map(x-> getRole(x.getRole()))
                .collect(Collectors.toList()));
        entitymanager.merge(user);
    }

    @Override
    public User updateUserAndReturn(User user) {
        return entitymanager.merge(user);
    }

    private Role getRole(String roleName) {
        return entitymanager.createQuery("SELECT DISTINCT r FROM Role r WHERE r.role = :roleName" , Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }
}