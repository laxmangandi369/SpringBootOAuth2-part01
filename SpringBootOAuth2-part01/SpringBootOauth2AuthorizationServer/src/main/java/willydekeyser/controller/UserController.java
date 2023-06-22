package willydekeyser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import willydekeyser.entity.SecurityUser;
import willydekeyser.service.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UsersService usersService;

   /* @Autowired
    private MyUserService service;*/

    @GetMapping("users")
    public List<SecurityUser> getUsers() {
        return usersService.getUsers();
    }

/*
    @PostMapping("/createUser")
    public void addUser(@RequestBody User user){
        service.addUser(user);
    }*/


}




    /*
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return usersService.addUser(userInfo);
    }*/

   /* @PostMapping("/create")
    public String createUser(@RequestBody SecurityUser securityUser){
        userRepos.save(securityUser);
        return "user is saved";
    }*/