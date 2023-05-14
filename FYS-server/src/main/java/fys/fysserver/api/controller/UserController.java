package fys.fysserver.api.controller;

import fys.fysmodel.User;
import fys.fysserver.api.model.LoginResponse;
import fys.fysserver.api.model.RegisterResponse;
import fys.fysserver.api.service.UserService;
import fys.fysserver.api.model.LoginRequest;
import fys.fysserver.api.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(user, true, null);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
       // try {
            User user = userService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getFirstName(), registerRequest.getLastName());
            return new RegisterResponse(user, true, null);
        //}
        //catch(Exception e) {
         //   return new RegisterResponse(null, false, e.getMessage());
        //}
    }

}
