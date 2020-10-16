package webBackend.service;

import webBackend.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
    Role getRole(String roleName);
}
