package fys.fysserver.api.controller;

import fys.fysmodel.User;
import fys.fysserver.api.model.LoginResponse;
import fys.fysserver.api.model.RegisterResponse;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.security.services.UserDetailsImpl;
import fys.fysserver.api.service.UserService;
import fys.fysserver.api.model.LoginRequest;
import fys.fysserver.api.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    JwtUtils jwtUtils;

    private UserService userService;

    public UserController() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
//            return new LoginResponse(user, true, null);
//        }
//        catch(Exception e) {
//            return new LoginResponse(null, false, e.getMessage());
//        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(),
                userDetails.getUsername(), userDetails.getPassword(), roles));
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("register: " + registerRequest.getUsername() + " " + registerRequest.getPassword() + " " + registerRequest.getEmail());
       // try {
            User user = userService.register(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getEmail());
            return new RegisterResponse(user, true, null);
        //}
        //catch(Exception e) {
         //   return new RegisterResponse(null, false, e.getMessage());
        //}
    }

}
