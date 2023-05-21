package fys.fysserver.api.controller;

import fys.fysserver.api.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class    AnnouncementController {
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
