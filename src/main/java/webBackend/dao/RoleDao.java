package webBackend.dao;

import webBackend.model.Role;

public interface RoleDao {
    void addRole(Role role);
    Role getRoleByName(String roleName);
}
