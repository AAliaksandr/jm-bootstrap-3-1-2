package webBackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webBackend.service.RoleManager;
import webBackend.model.Role;
import webBackend.model.User;
import webBackend.service.CreateFakeUsers;
import webBackend.service.StringsToUserConverter;
import webBackend.service.UserService;

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
    private final RoleManager roleManager;


    public UserController(UserService userService, Validator validator, CreateFakeUsers createFakeUsers, RoleManager roleManager) {
        this.userService = userService;
        this.validator = validator;
        this.createFakeUsers = createFakeUsers;
        this.roleManager = roleManager;
    }

    @PostConstruct
    public void fillInTables() throws IOException {
        for(long i = 1L; i <= 9; i++) {
            User user = new User(createFakeUsers.getNames().get((int) i),
                    createFakeUsers.getLastNames().get((int) i),
                    i,
                    createFakeUsers.transliterate(createFakeUsers.getNames().get((int) i)) + "@mail.ru",
                    "123456");
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setRole("USER");
            roles.add(role);
            user.setRoles(roles);
            userService.addUser(user);
        }
        User user2 = new User("user", "de user", 32, "user@mail.ru", "user");
        List<Role> roles2 = new ArrayList<>();
        Role role2 = new Role();
        role2.setRole("USER");
        roles2.add(role2);
        user2.setRoles(roles2);

        userService.addUser(user2);

        User admin2 = new User("admin", "de admin", 42, "admin@mail.ru", "admin");
        List<Role> adminRoles = new ArrayList<>();
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        adminRoles.add(adminRole);
        admin2.setRoles(adminRoles);

        userService.addUser(admin2);
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
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authorisedUser", userService.getUserByEmail(principal.getName()));
        model.addAttribute("title", "Admin Profile");
        model.addAttribute("newUser", new User());
        model.addAttribute("allTheRoles", roleManager);
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
            return "/admin/adduser";
        }
        model.addAttribute("added", "yes");
        return "redirect:/admin";
    }


    @PostMapping("/admin/update-user")
    public String doUpdateUser(@ModelAttribute RoleManager allTheRoles, @RequestParam Map<String, String> updateUser, Model model) {
        //----------------------------------
/*        updateUser.entrySet().stream()
                .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));*/
        //================================================
        User updateUSer = new StringsToUserConverter().convert(updateUser);
        updateUSer.setRoles(allTheRoles.getUpdatedNewRoles());
        userService.updateUser(updateUSer);
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