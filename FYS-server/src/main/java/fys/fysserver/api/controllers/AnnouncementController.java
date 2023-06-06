package fys.fysserver.api.controllers;

import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.announcements.NewAnnouncementDto;
import fys.fysserver.api.dtos.announcements.NewReservationDto;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AnnouncementController {
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    private JwtUtils jwtUtils;

    private AnnouncementService announcementService;

    public AnnouncementController() {
        logger.info("Announcement Controller instantiated");
    }

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        logger.info("Announcement Service set");
        this.announcementService = announcementService;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        logger.info("JwtUtils set");
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/my-announcements")
    public ResponseEntity<?> getMyAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "search-query", defaultValue = "") String searchQuery,
            @RequestParam(name = "search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name = "page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name = "page-size", defaultValue = "10") String pageSize
    ) {
        logger.info("authorized getAnnouncementInvoked for query" + searchQuery);
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);

            return new ResponseEntity<>(
                    announcementService.getMyAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
            logger.info("Validation Exception threw: " + e.toString());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/announcements")
    public ResponseEntity<?> getAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "search-query", defaultValue = "") String searchQuery,
            @RequestParam(name = "search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name = "page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name = "page-size", defaultValue = "10") String pageSize
    ) {
        logger.info("unauthorized getAnnouncementInvoked for query" + searchQuery);

        String username = extractUsernameFromAuthorizationHeader(authorizationHeader);

        return new ResponseEntity<>(
                announcementService.getAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @GetMapping("/announcements/{announcementId}/reservations")
    public ResponseEntity<?> getAnnouncementReservations(
            @PathVariable Integer announcementId
    ) {
        logger.info("get reservation by announcement id: " + announcementId.toString());

        try {
            return new ResponseEntity<>(
                    announcementService.getAnnouncementReservations(announcementId),
                    HttpStatus.OK
            );
        }
        catch (ValidationException e) {
            logger.info("Validation Exception threw: " + e.toString());
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    @PostMapping("/reservations")
    public ResponseEntity<?> addReservations(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody NewReservationDto newReservationDTO
    ) {
        logger.info("add reservation invoked: " + newReservationDTO);
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            announcementService.addReservation(username, newReservationDTO);

            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        }
        catch (ValidationException e) {
            logger.info("validation exception threw: " + e.toString());
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/fields")
    public ResponseEntity<?> getAnnouncementFields() {
        logger.info("getAnnouncementFields invoked");
        return new ResponseEntity<>(announcementService.getAnnouncementFields(), HttpStatus.OK);
    }

    @GetMapping("/favourites")
    public ResponseEntity<?> getFavouriteAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        logger.info("get favourite announcements invoked");
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            return new ResponseEntity<>(
                    announcementService.getFavouriteAnnouncements(username),
                    HttpStatus.OK
            );
        }
        catch(ValidationException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/favourites/{announcementId}")
    public ResponseEntity<?> addAnnouncementToFavourites(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Integer announcementId
    ) {
        logger.info("add announcement to favourites invoked");
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);

            announcementService.addAnnouncementToFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (ValidationException e) {
            logger.info("exception threw: " + e.toString());
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/favourites/{announcementId}")
    public ResponseEntity<?> removeAnnouncementFromFavourites(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Integer announcementId
    ) {
        logger.info("removeAnnouncementFromFavourites invoked");
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);

            announcementService.removeAnnouncementFromFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (ValidationException e) {
            logger.info("validation exception threw: " + e.toString());
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/announcements")
    public ResponseEntity<?> addAnnouncement(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody NewAnnouncementDto newAnnouncementDto
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Adding announcement for user: {}", username);

            AnnouncementDto announcement = announcementService.addAnnouncement(username, newAnnouncementDto);

            return new ResponseEntity<>(announcement, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to add announcement: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-schedules")
    public ResponseEntity<?> getMySchedules(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Retrieving schedules for user: {}", username);

            return new ResponseEntity<>(announcementService.getMySchedule(username), HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to retrieve schedules: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/my-schedules/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Integer scheduleId
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Deleting schedule {} for user: {}", scheduleId, username);

            announcementService.deleteSchedule(username, scheduleId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to delete schedule: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<?> getMyReservations(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Retrieving reservations for user: {}", username);

            return new ResponseEntity<>(announcementService.getMyReservations(username), HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to retrieve reservations: {}", e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/my-reservations/{reservationId}")
    public ResponseEntity<?> deleteReservation(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Integer reservationId
    ) {
        try {
            String username = extractUsernameFromAuthorizationHeader(authorizationHeader);
            logger.info("Deleting reservation {} for user: {}", reservationId, username);

            announcementService.deleteReservation(username, reservationId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (ValidationException e) {
            logger.info("Failed to delete reservation: {}", e.getMessage());
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
