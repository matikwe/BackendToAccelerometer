package org.example.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Map<String, String> registerNewUser(
            @RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PostMapping(path = "login")
    public Map<String, String> verifyLoginDetails(
            @RequestBody User user) {
        return userService.verifyLoginDetails(user.getLogin(), user.getPassword());
    }

    @PutMapping(path = "editTotalJumps/{userId}")
    public void editTotalJumps(
            @PathVariable("userId") Long userId,
            @RequestParam Long totalJumps){
        userService.editTotalJumps(userId,totalJumps);
    }
}
