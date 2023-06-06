package fys.fysserver.api.controllers;

import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.users.*;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private JwtUtils jwtUtils;
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

    @GetMapping("/users")
    public ResponseEntity<?> getUser(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Getting user: {}", username);

            UserDto user = userService.findUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to get user: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/specialists")
    public ResponseEntity<?> getSpecialist(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Getting specialist: {}", username);

            SpecialistDto specialistDto = userService.findSpecialistByUsername(username);

            return new ResponseEntity<>(specialistDto, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to get specialist: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/specialists")
    public ResponseEntity<?> upgradeUser(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Upgrading user to specialist: {}", username);

            userService.upgradeUserToSpecialist(username);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to upgrade user to specialist: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserDto updatedUserDto
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Updating user: {}", username);

            UserDto userDto = userService.updateUser(username, updatedUserDto);

            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to update user: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/specialists")
    public ResponseEntity<?> updateSpecialist(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody SpecialistDto updatedSpecialistDto
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Updating specialist: {}", username);

            SpecialistDto specialistDto = userService.updateSpecialist(username, updatedSpecialistDto);

            return new ResponseEntity<>(specialistDto, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to update specialist: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/recently-visited-announcements")
    public ResponseEntity<?> getRecentlyVisitedAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
        logger.info("Getting recently visited announcements for user: {}", username);

        List<AnnouncementDto> recentlyVisitedAnnouncements =
                userService.getRecentlyVisitedAnnouncements(username);

        return new ResponseEntity<>(recentlyVisitedAnnouncements, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody NewLoginDto newLoginDto) {
        try {
            logger.info("User login: {}", newLoginDto.getUsername());

            LoginDto loginDto = userService.login(newLoginDto);
            return new ResponseEntity<>(loginDto, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to login: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NewRegistrationDto newRegistrationDto) {
        try {
            logger.info("User registration: {}", newRegistrationDto.getUsername());

            UserDto registrationDto = userService.register(newRegistrationDto);
            return new ResponseEntity<>(registrationDto, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to register user: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    private String extractUsernameFromAuthorizationHeader(String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7);
            return jwtUtils.getUsernameFromJwtToken(token);
        } catch (Exception ignored) {
            return null;
        }
    }
}


