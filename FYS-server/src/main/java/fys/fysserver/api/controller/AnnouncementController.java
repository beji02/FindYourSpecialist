package fys.fysserver.api.controller;

import fys.fysmodel.Announcement;
import fys.fysserver.api.model.AddAnnouncementRequest;
import fys.fysserver.api.model.AddAnnouncementResponse;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/myannouncements")
    public Iterable getMyAnnouncements(
            HttpServletRequest request,
            @RequestParam(name="search-query", defaultValue = "") String searchQuery,
            @RequestParam(name="search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name="page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name="page-size", defaultValue = "10") String pageSize
    ) {
        System.out.println("addMyAnnouncement");
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader: " + authorizationHeader);
        String token = extractTokenFromAuthorizationHeader(authorizationHeader);
        String username = jwtUtils.getUsernameFromJwtToken(token);
        System.out.println("username: " + username);
        return announcementService.getMyAnnouncements(username, searchQuery, searchCategories, pageNumber, pageSize);
    }

    @GetMapping("/announcements")
    public Iterable getAnnouncements(
            @RequestParam(name="search-query", defaultValue = "") String searchQuery,
            @RequestParam(name="search-categories", defaultValue = "") String searchCategories,
            @RequestParam(name="page-number", defaultValue = "0") String pageNumber,
            @RequestParam(name="page-size", defaultValue = "10") String pageSize
    ) {
        return announcementService.getAnnouncements(searchQuery, searchCategories, pageNumber, pageSize);
    }

    @GetMapping("/announcements/fields")
    public Iterable getAnnouncementFields() {
        return announcementService.getAnnouncementFields();
    }



    @PostMapping("/announcements")
    public AddAnnouncementResponse addAnnouncement(HttpServletRequest request, @RequestBody AddAnnouncementRequest addAnnouncementRequest) {
        try {
            System.out.println("addAnnouncement");
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorizationHeader: " + authorizationHeader);
            String token = extractTokenFromAuthorizationHeader(authorizationHeader);
            String username = jwtUtils.getUsernameFromJwtToken(token);
            System.out.println("username: " + username);
            Announcement announcement = announcementService.addAnnouncement(username, addAnnouncementRequest.getTitle(),
                    addAnnouncementRequest.getDescription(), addAnnouncementRequest.getStartDate(),
                    addAnnouncementRequest.getEndDate(), addAnnouncementRequest.getFieldId());
            return new AddAnnouncementResponse(announcement, true, null);
        } catch (Exception e) {
            return  new AddAnnouncementResponse(null, false, e.getMessage());
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