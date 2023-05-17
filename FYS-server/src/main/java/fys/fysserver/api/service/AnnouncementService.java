package fys.fysserver.api.service;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import fys.fyspersistence.specialists.SpecialistsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AnnouncementService {
    private AnnouncementsRepository announcementsRepository;
    private SpecialistsRepository specialistsRepository;

    public void setAnnouncementsRepository(AnnouncementsRepository announcementsRepository) {
        this.announcementsRepository = announcementsRepository;
    }

    public void setSpecialistsRepository(SpecialistsRepository specialistsRepository) {
        this.specialistsRepository = specialistsRepository;
    }

    public AnnouncementService() {
    }

    public Announcement addAnnouncement(String username, String title, String description, LocalDate startDate, LocalDate endDate) {
        System.out.println("addAnnouncement: " + username + " " + title + " " + description + " " + startDate + " " + endDate);

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setStartDate(startDate);
        announcement.setEndDate(endDate);
        announcement.setRate(0f);

        Announcement addedAnnouncement = announcementsRepository.add(announcement);

        Specialist specialist = specialistsRepository.findSpecialistByUsername(username);
        specialist.addAnnouncement(addedAnnouncement);
        specialistsRepository.modify(specialist);

        return announcement;
    }
}
