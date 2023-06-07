package fys.fysserver.api.controllers;

import fys.fysmodel.Notification;
import fys.fysserver.api.dtos.DtoUtils;
import fys.fysserver.api.dtos.announcements.AnnouncementDto;
import fys.fysserver.api.dtos.announcements.NewAnnouncementDto;
import fys.fysserver.api.dtos.announcements.NewReservationDto;
import fys.fysserver.api.exceptions.ValidationException;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.services.AnnouncementService;
import fys.fysserver.api.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AnnouncementController {
    private JwtUtils jwtUtils;
    private AnnouncementService announcementService;
    private MessageSendingOperations<String> messagingTemplate;
    private SimpUserRegistry userRegistry;

    @Autowired
    public AnnouncementController(
            JwtUtils jwtUtils,
            AnnouncementService announcementService,
            @Qualifier("brokerMessagingTemplate") MessageSendingOperations<String> messagingTemplate,
            SimpUserRegistry userRegistry
    ) {
        this.jwtUtils = jwtUtils;
        this.announcementService = announcementService;
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
    }


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
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "search-query", defaultValue = "") String searchQuery,
            @RequestParam(name = "search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name = "page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name = "page-size", defaultValue = "10") String pageSize
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            return new ResponseEntity<>(
                    announcementService.getMyAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize),
                    HttpStatus.OK
            );
        } catch (ValidationException e) {
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
        String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

        return new ResponseEntity<>(
                announcementService.getAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @GetMapping("/announcements/{announcementId}/reservations")
    public ResponseEntity<?> getAnnouncementReservations(
            @PathVariable Integer announcementId
    ) {
        try {
            return new ResponseEntity<>(
                    announcementService.getAnnouncementReservations(announcementId),
                    HttpStatus.OK
            );
        }
        catch (ValidationException e) {
            return new ResponseEntity<>(
                    e.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    @PostMapping("/reservations")
    public ResponseEntity<?> addReservations(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody NewReservationDto newReservationDto
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);
            Notification notification = announcementService.addReservation(username, newReservationDto);

            try {
                if (messagingTemplate instanceof SimpMessagingTemplate) {
                    SimpMessagingTemplate simpMessagingTemplate = (SimpMessagingTemplate) messagingTemplate;
                    if (userRegistry.getUser(username) != null) {
                        simpMessagingTemplate.convertAndSendToUser(username, "/topic/reservation", DtoUtils.buildNotificationDTO(notification));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/fields")
    public ResponseEntity<?> getAnnouncementFields() {
        return new ResponseEntity<>(announcementService.getAnnouncementFields(), HttpStatus.OK);
    }

    @GetMapping("/favourites")
    public ResponseEntity<?> getFavouriteAnnouncements(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

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
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            announcementService.addAnnouncementToFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (ValidationException e) {
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
        try {
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            announcementService.removeAnnouncementFromFavourites(username, announcementId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (ValidationException e) {
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
            String username = HeadersUtils.extractTokenFromAuthorizationHeader(authorizationHeader, jwtUtils);

            AnnouncementDto announcement =
                    announcementService.addAnnouncement(username, newAnnouncementDto);

            return new ResponseEntity<>(announcement, HttpStatus.OK);
        }
        catch (ValidationException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
