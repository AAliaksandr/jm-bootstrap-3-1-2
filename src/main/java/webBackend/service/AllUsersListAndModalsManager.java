package webBackend.service;

import org.springframework.stereotype.Component;
import webBackend.model.User;

import java.util.ArrayList;
import java.util.List;


public class AllUsersListAndModalsManager {

    private List<User> allUsersForListAndModals = new ArrayList<>();


    public List<User> getAllUsersForListAndModals() {
        return allUsersForListAndModals;
    }

    public void setAllUsersForListAndModals(List<User> allUsersForListAndModals) {
        this.allUsersForListAndModals = allUsersForListAndModals;
    }

    public AllUsersListAndModalsManager AllUsersForListAndModalsAndGetTheObject(List<User> allUsersForListAndModals) {
        this.allUsersForListAndModals = allUsersForListAndModals;
        return this;
    }
}