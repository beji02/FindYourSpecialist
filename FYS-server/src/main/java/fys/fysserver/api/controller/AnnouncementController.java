<<<<<<< HEAD
package fys.fysserver.api.controller;

import fys.fysmodel.Announcement;
import fys.fysserver.api.model.AddAnnouncementRequest;
import fys.fysserver.api.model.AddAnnouncementResponse;
import fys.fysserver.api.security.jwt.JwtUtils;
import fys.fysserver.api.service.AnnouncementService;
import fys.fysserver.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/addannouncement")
    public ResponseEntity<?> addAnnouncement(@RequestBody AddAnnouncementRequest addAnnouncementRequest) {
        try {
            String username = jwtUtils.getUsernameFromJwtToken(addAnnouncementRequest.getToken());
            Announcement announcement = announcementService.addAnnouncement(username, addAnnouncementRequest.getTitle(),
                    addAnnouncementRequest.getDescription(), addAnnouncementRequest.getStartDate(),
                    addAnnouncementRequest.getEndDate());
            return new ResponseEntity<>(new AddAnnouncementResponse(announcement, true, null), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(new AddAnnouncementResponse(null, false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
=======
package fys.fysserver.api.controller;

import fys.fysserver.api.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnouncementController {
    private AnnouncementService announcementService;

    public AnnouncementController() {
    }

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
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
}
>>>>>>> master
