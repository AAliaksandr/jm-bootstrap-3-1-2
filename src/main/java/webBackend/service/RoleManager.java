package webBackend.service;

import org.springframework.stereotype.Service;
import webBackend.model.Role;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleManager implements Serializable {
    private final String ADMIN = "ADMIN";
    private final String USER = "USER";
    private List<String> allFields = new ArrayList<>();
    private List<String> newRoles = new ArrayList<>();

    public RoleManager() {
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(y -> Modifier.isFinal(y.getModifiers())).forEach(x -> this.allFields.add(x.getName()));
    }

    public String getADMIN() {
        return ADMIN;
    }

    public String getUSER() {
        return USER;
    }

    public List<String> getAllFields() {
        return allFields;
    }

    public RoleManager setAllFields() {
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(y -> Modifier.isFinal(y.getModifiers())).forEach(x -> this.allFields.add(x.getName()));
        return this;
    }

    public void setNewRoles(List<String> newRoles) {
        this.newRoles = newRoles;
    }

    public List<String> getNewRoles() {
        return this.newRoles;
    }

    public List<Role> getUpdatedNewRoles() {
//        System.out.println("the getRoles method in AllRoles");
        List<Role> roles = new ArrayList<>();
        if (!newRoles.isEmpty()) {
            for (String s : this.newRoles) {
                roles.add(new Role(s));
                System.out.println(s);
            }
        } else {
            roles.add(new Role("USER"));
        }
        return roles;
    }
}