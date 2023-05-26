package fys.fysserver.api.controller;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fysserver.api.model.*;
import fys.fysserver.api.model.specialist.SpecialistResponse;
import fys.fysserver.api.model.specialist.SpecialistUpdateRequest;
import fys.fysserver.api.model.user.UpdateType;
import fys.fysserver.api.model.user.UserResponse;
import fys.fysserver.api.model.user.UserUpdateRequest;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.security.services.UserDetailsImpl;
import fys.fysserver.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private static void checkToken(String token) {
        if(token == null) {
            throw new IllegalArgumentException("No token provided");
        }
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

    @PostMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        if(token == null) {
            return new ResponseEntity<String>("No token provided", HttpStatus.BAD_REQUEST);
        } else if(!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.FORBIDDEN);
        }

        User user = userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));
        return new ResponseEntity<>(UserResponse.build(user), HttpStatus.OK);
    }

    @PostMapping("/specialist")
    public ResponseEntity<?> getSpecialist(@RequestHeader("Authorization") String token) {
        if(token == null) {
            return new ResponseEntity<String>("No token provided", HttpStatus.BAD_REQUEST);
        } else if(!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.FORBIDDEN);
        }

        Specialist specialist = (Specialist) userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));
        return new ResponseEntity<>(SpecialistResponse.build(specialist), HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdateRequest userRequest) {
        if(token == null) {
            return new ResponseEntity<String>("No token provided", HttpStatus.BAD_REQUEST);
        } else if(!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.FORBIDDEN);
        }

        System.out.println("update type:" + userRequest.getUpdateType());
        if (userRequest.getUpdateType() == UpdateType.UPDATE_USER) {
            User user = userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(userRequest.getEmail());
            user.setBirthDate(userRequest.getBirthDate());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setOptionalDescription(userRequest.getOptionalDescription());

            userService.updateUser(user);

            return new ResponseEntity<>(UserResponse.build(user), HttpStatus.NO_CONTENT);
        } else {
            User user = userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));

            userService.upgradeToSpecialist(user);

            return new ResponseEntity<>(UserResponse.build(user), HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/specialist")
    public ResponseEntity<?> updateSpecialist(@RequestHeader("Authorization") String token, @RequestBody SpecialistUpdateRequest specialistUpdateRequest) {
        if(token == null) {
            return new ResponseEntity<String>("No token provided", HttpStatus.BAD_REQUEST);
        } else if(!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.FORBIDDEN);
        }

        Specialist specialist = (Specialist) userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));
        specialist.setLocation(specialistUpdateRequest.getLocation());
        specialist.setDescription(specialistUpdateRequest.getDescription());

        try {
            userService.updateSpecialist(specialist);

            return new ResponseEntity<>(SpecialistResponse.build(specialist), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/recently-visited-announcements")
    public ResponseEntity<?> getRecentlyVisitedAnnouncements(@RequestHeader("Authorization") String token) {
        if(token == null) {
            return new ResponseEntity<String>("No token provided", HttpStatus.BAD_REQUEST);
        } else if(!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.FORBIDDEN);
        }

        User user = userService.findUserByUsername(jwtUtils.getUsernameFromJwtToken(token));
        Iterable<Announcement> announcements = user.getRecentlyVisitedAnnouncements();

        System.out.println("recently visited announcements: " + ((List)announcements).size());

        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
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
