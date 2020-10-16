package webBackend.service;

import webBackend.model.Role;

public interface RoleService {
    void addRole(Role role);
    Role getRoleByName(String roleName);
}
