package webBackend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRoles implements Serializable {
    private final String admin = "ADMIN";
    private final String user = "USER";

    public AllRoles() {
    }

    public String getAdmin() {
        return admin;
    }

    public String getUser() {
        return user;
    }

    public List<String> getAllRoles() {
        List<String> allRoles = new ArrayList<>();
        allRoles.add(this.getAdmin());
        allRoles.add(this.getUser());
        return allRoles;
    }
}
