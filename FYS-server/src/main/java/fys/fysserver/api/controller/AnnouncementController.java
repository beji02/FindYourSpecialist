package fys.fysserver.api.controller;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysserver.api.dto.AnnouncementDTO;
import fys.fysserver.api.dto.ReservationDTO;
import fys.fysserver.api.model.*;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.service.AnnouncementService;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AnnouncementController {
    private JwtUtils jwtUtils;

    private AnnouncementService announcementService;

    public AnnouncementController() {
    }

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/my-announcements")
    public ResponseEntity<?> getMyAnnouncements(
            HttpServletRequest request,
            @RequestParam(name = "search-query", defaultValue = "") String searchQuery,
            @RequestParam(name = "search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name = "page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name = "page-size", defaultValue = "10") String pageSize
    ) {
        try {
            System.out.println("addMyAnnouncement");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);

            return new ResponseEntity<>(
                    announcementService.getMyAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize),
                    HttpStatus.OK
            );
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/announcements")
    public ResponseEntity<?> getAnnouncements(
            HttpServletRequest request,
            @RequestParam(name = "search-query", defaultValue = "") String searchQuery,
            @RequestParam(name = "search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name = "page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name = "page-size", defaultValue = "10") String pageSize
    ) {
        try {
            System.out.println("getAnnouncements");
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null) {
                return new ResponseEntity<>(
                        announcementService.getFilteredAnnouncementsWithFavourites(null, searchQuery, searchCategories, pageNumber, pageSize),
                        HttpStatus.OK
                );
            }
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            return new ResponseEntity<>(
                    announcementService.getFilteredAnnouncementsWithFavourites(username, searchQuery, searchCategories, pageNumber, pageSize),
                    HttpStatus.OK
            );
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> addReservations(
            HttpServletRequest request,
            @RequestBody ReservationDTO reservationDTO
    ) {
        try {
            System.out.println("addReservation");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            announcementService.addReservation(username, reservationDTO);

            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/fields")
    public ResponseEntity<?> getAnnouncementFields() {
        try {
            return new ResponseEntity<>(
                    announcementService.getAnnouncementFields(),
                    HttpStatus.OK
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/favourites")
    public ResponseEntity<?> getFavouriteAnnouncements(HttpServletRequest request) {
        try {
            System.out.println("getFavouriteAnnouncements");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            return new ResponseEntity<>(
                    announcementService.getFavouriteAnnouncements(username),
                    HttpStatus.OK
            );
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @PutMapping("/favourites/{announcementId}")
    public ResponseEntity<?> addAnnouncementToFavourites(
            HttpServletRequest request,
            @PathVariable Integer announcementId
    ) {
        try {
            System.out.println("addAnnouncementToFavourites");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            announcementService.addAnnouncementToFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/favourites/{announcementId}")
    public ResponseEntity<?> removeAnnouncementFromFavourites(HttpServletRequest request, @PathVariable Integer announcementId) {
        try {
            System.out.println("removeAnnouncementFromFavourites");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            announcementService.removeAnnouncementFromFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @PostMapping("/announcements")
    public ResponseEntity<?> addAnnouncement(
            HttpServletRequest request,
            @RequestBody AddAnnouncementRequest addAnnouncementRequest
    ) {
        try {
            System.out.println("addAnnouncement");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            Announcement announcement = announcementService.addAnnouncement(username, addAnnouncementRequest.getTitle(),
                    addAnnouncementRequest.getDescription(), null,
                    null, addAnnouncementRequest.getFieldId(),
                    addAnnouncementRequest.getWorkDays()
            );

            return new ResponseEntity<>(new AddAnnouncementResponse(announcement, true, null), HttpStatus.OK);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AddAnnouncementResponse(null, false, "Unauthorized"),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (HttpClientErrorException.BadRequest e) {
            return new ResponseEntity<>(
                    new AddAnnouncementResponse(null, false, e.toString()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AddAnnouncementResponse(null, false, e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            System.out.println("extractTokenFromAuthorizationHeader");
            return authorizationHeader.substring(7); // Remove the "Bearer Token" prefix
        }
        return null;
    }
}
