package org.example.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path = "register")
    public User registerNewUser(
            @RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PostMapping(path = "login")
    public User verifyLoginDetails(
            @RequestBody User user) {
        return userService.verifyLoginDetails(user.getLogin(), user.getPassword());
    }
}
