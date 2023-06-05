package fys.fysserver.api.controllers;

import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.users.*;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.services.UserService;
import fys.fysserver.api.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

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
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            UserDto user = userService.findUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/specialists")
    public ResponseEntity<?> getSpecialist(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            SpecialistDto specialistDto = userService.findSpecialistByUsername(username);

            return new ResponseEntity<>(specialistDto, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/specialists")
    public ResponseEntity<?> upgradeUser(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            userService.upgradeUserToSpecialist(username);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserDto updatedUserDto
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);
            UserDto userDto = userService.updateUser(username, updatedUserDto);

            return new ResponseEntity<>(userDto, HttpStatus.OK);

        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/specialists")
    public ResponseEntity<?> updateSpecialist(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody SpecialistDto updatedSpecialistDto
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);
            SpecialistDto specialistDto = userService.updateSpecialist(username, updatedSpecialistDto);

            return new ResponseEntity<>(specialistDto, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/recently-visited-announcements")
    public ResponseEntity<?> getRecentlyVisitedAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

        List<AnnouncementDto> recentlyVisitedAnnouncements =
                userService.getRecentlyVisitedAnnouncements(username);

        return new ResponseEntity<>(recentlyVisitedAnnouncements, HttpStatus.OK);
    }

    @GetMapping("/users/notifications")
    public ResponseEntity<?> getNotifications(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            //System.out.println("username: " + username);
            List<NotificationDto> notifications =
                    userService.getNotifications(username);

            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/notifications/{notificationId}")
    public ResponseEntity<?> readNotification(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("notificationId") Integer notificationId
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            userService.readNotification(username, notificationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/users/recently-visited-announcements")
    public ResponseEntity<?> addRecentlyVisitedAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody RecentlyAnnouncementDto recentlyAnnouncementDto
    ) {
        try {
            System.out.println("announcementId: " + recentlyAnnouncementDto.getAnnouncementId());
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            userService.addRecentlyVisitedAnnouncement(username, recentlyAnnouncementDto.getAnnouncementId());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody NewLoginDto newLoginDto) {
        try {
            LoginDto loginDto = userService.login(newLoginDto);
            return new ResponseEntity<>(loginDto, HttpStatus.OK);
        }
        catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NewRegistrationDto newRegistrationDto) {
        try{
            UserDto registrationDto = userService.register(newRegistrationDto);
            return new ResponseEntity<>(registrationDto, HttpStatus.OK);
        }
        catch(ValidationException e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

