package webBackend.service;

import org.springframework.stereotype.Component;
import webBackend.model.Role;

@Component
public class initializeRoleTable {
    private final UserService userService;

    public initializeRoleTable(UserService userService) {
        this.userService = userService;
    }
    public void fillIn() {
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        userService.fillRoleIntoTable(userRole);
        userService.fillRoleIntoTable(adminRole);
    }
}