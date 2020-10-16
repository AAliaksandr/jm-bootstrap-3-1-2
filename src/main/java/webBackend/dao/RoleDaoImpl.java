package webBackend.dao;

import org.springframework.stereotype.Repository;
import webBackend.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    
    @PersistenceContext
    private EntityManager entitymanager;
    
    @Override
    public void addRole(Role role) {
            entitymanager.persist(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return entitymanager.createQuery("SELECT r FROM Role r WHERE r.role = :roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entitymanager.createQuery("SELECT DISTINCT r FROM Role r ORDER BY r.id ", Role.class).getResultList();
    }

    @Override
    public Role getRole(String roleName) {
        return entitymanager.createQuery("SELECT DISTINCT r FROM Role r WHERE r.role = :roleName" , Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }
}