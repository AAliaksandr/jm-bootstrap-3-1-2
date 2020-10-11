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
    private List<String> allRoles = new ArrayList<>();

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

    public void setAllRoles(List<String> allRoles) {
        this.allRoles = allRoles;
    }

    public List<String> getAllRoles() {

        return this.allRoles;
    }

    public List<Role> getRoles() {
        System.out.println("the getRoles method in AllRoles");
        List<Role> roles = new ArrayList<>();
        for (String  s : this.allRoles) {
            roles.add(new Role(s));
            System.out.println(s);
        }

        return roles;
    }
}
