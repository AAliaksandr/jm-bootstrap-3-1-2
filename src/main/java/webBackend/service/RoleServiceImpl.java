package webBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webBackend.dao.RoleDao;
import webBackend.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public Role getRole(String roleName) {
        return roleDao.getRole(roleName);
    }
}
