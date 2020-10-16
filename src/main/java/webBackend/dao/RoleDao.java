package webBackend.dao;

import webBackend.model.Role;

import java.util.List;

public interface RoleDao {
    void addRole(Role role);
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
}
