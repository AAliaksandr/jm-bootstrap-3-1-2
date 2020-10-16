package webBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webBackend.dao.RoleDao;
import webBackend.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }
}
