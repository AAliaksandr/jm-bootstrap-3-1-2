package webBackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webBackend.service.*;
import webBackend.model.Role;
import webBackend.model.User;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;
    private final Validator validator;
    private final CreateFakeUsers createFakeUsers;
    private final AllUsersListAndModalsManager allUsersAndModals;


    public UserController(UserService userService, Validator validator, CreateFakeUsers createFakeUsers, AllUsersListAndModalsManager allUsersAndModals) {
        this.userService = userService;
        this.validator = validator;
        this.createFakeUsers = createFakeUsers;
        this.allUsersAndModals = allUsersAndModals;
    }

    @PostConstruct
    public void fillInTables() throws IOException {
        createFakeUsers.createFakeUsers();
    }

    @GetMapping(value = {"/"})
    public String listAllUsers(@RequestParam Map<String, String> params, Model model) {

        getAllUsers(model);
        model.addAttribute("title", "All users");

        /* there may come:
            added - the added user
            dname - the deleted user's name
            dsname - the deleted user's secondName
        */
        Optional.ofNullable(params).ifPresent(model::addAllAttributes);
        return "index";
    }

    @GetMapping(value = "/admin")
    public String getAdminProfile(Principal principal, Model model) {
//        model.addAttribute("allUsers", userService.getAllUsers());
        List<User> allUsers = userService.getAllUsers();
        // For unsetting a password to work with no side effects like deleting all users' passwords
        // I had to make a hint "readOnly" in the fetch query
        // otherwise the Entities would still be in the managed state
        allUsers.forEach(x -> x.setPassword(""));
        model.addAttribute("allUsers", allUsersAndModals.AllUsersForListAndModalsAndGetTheObject(allUsers));
        model.addAttribute("authorisedUser", userService.getUserByEmail(principal.getName()));
        model.addAttribute("title", "Admin Profile");
        model.addAttribute("newUser", new User());
        model.addAttribute("allTheRoles", userService.getAllRoles());
        model.addAttribute("userToUpdate", new User());
        return "admin-or-user";
    }

    @GetMapping(value = "/user")
    public String getUserProfile(Principal principal, Model model) {
        model.addAttribute("authorisedUser", userService.getUserByEmail(principal.getName()));
        model.addAttribute("title", "User Profile");
        return "admin-or-user";
    }

    @PostMapping("/admin/adduser")
    public String getSubmittedAddUserForm(@ModelAttribute User newUser, Model model) {
        try {
            userService.addUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "There has been an error: " + e.fillInStackTrace());
            return "/error";
        }
        model.addAttribute("added", "yes");
        return "redirect:/admin";
    }


/*    @PostMapping("/admin/update-user")
    public String doUpdateUser(@ModelAttribute RoleManager allTheRoles, @RequestParam Map<String, String> updateUser, Model model) {
        //----------------------------------
*//*        updateUser.entrySet().stream()
                .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));*//*
        //================================================
        User updateUser = new StringsToUserConverter().convert(updateUser);
        updateUSer.setRoles(allTheRoles.getUpdatedNewRoles());
        userService.updateUser(updateUser);
        model.addAttribute("updated_user", updateUser);
        model.addAttribute("title", "Corrected user");
        return "redirect:/admin";
    }*/

    @PostMapping("/admin/update-user/{id}")
    public String doUpdateUser(@ModelAttribute AllUsersListAndModalsManager allUsers, @PathVariable int id, Model model) {
        User updateUser = allUsers.getAllUsersForListAndModals().get( allUsers.getAllUsersForListAndModals().size() - 1);

        if (updateUser.getRoles().isEmpty()) {
            List<Role> role = new ArrayList<>();
            role.add(userService.getRole("USER"));
            updateUser.setRoles(role);
        }

        if (updateUser.getPassword().isEmpty()) {
            updateUser.setPassword(userService.getUserById(updateUser.getId()).getPassword());
        }
        userService.updateUser(updateUser);
        model.addAttribute("updated_user", updateUser);
        model.addAttribute("title", "Corrected user");
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser (@RequestParam("id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/signup")
    public String getSignUpForm(Model model) {
        model.addAttribute("title", "Sign Up");
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String onSignUp(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Sign Up");
            model.addAllAttributes(bindingResult.getFieldErrors());
            return "signup";
        }
        model.addAttribute("title", "Sign Up");
        model.addAttribute("user", user);
        return "signup";
    }

    private void getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        int count = users.size();
        model.addAttribute("users", users);
        model.addAttribute("count", count);
    }
}