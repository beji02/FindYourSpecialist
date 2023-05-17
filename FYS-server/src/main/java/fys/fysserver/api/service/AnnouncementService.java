<<<<<<< HEAD
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
=======
package fys.fysserver.api.service;

import fys.fysmodel.Announcement;
import fys.fyspersistence.announcements.AnnouncementsDbRepository;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AnnouncementService {
    private AnnouncementsRepository announcementsRepository;

    public Iterable<Announcement> getAnnouncements(String searchQuery, String searchCategories, String pageNumber, String pageSize) {
        List<Announcement> allAnnouncements = (List<Announcement>) announcementsRepository.findAll();
        List<Announcement> announcements = new ArrayList<>();

        if(!searchCategories.isEmpty()) {
            // split the searchCategories into words
            List<String> searchCategoriesList = new ArrayList<>(List.of(searchCategories.split(" ")));
            //make lowercase
            searchCategoriesList.replaceAll(String::toLowerCase);

            // for each announcement
            for (Announcement announcement : allAnnouncements) {
                // for each word in searchCategories
                for (String searchCategory : searchCategoriesList) {
                    // if the announcement contains the word
                    if (announcement.getField().getName().toLowerCase().equals(searchCategory)) {
                        // add the announcement to the list of announcements
                        announcements.add(announcement);

                        break;
                    }
                }
            }
            allAnnouncements = announcements;
            announcements = new ArrayList<>();
        }

        if (!searchQuery.isEmpty()) {
            // split the searchQuery into words
            List<String> searchWords = new ArrayList<>(List.of(searchQuery.split(" ")));
            //make lowercase
            searchWords.replaceAll(String::toLowerCase);

            // for each announcement
            for (Announcement announcement : allAnnouncements) {
                // for each word in searchQuery
                for (String searchWord : searchWords) {
                    // if the announcement contains the word
                    if (announcement.getTitle().toLowerCase().contains(searchWord)) {
                        // add the announcement to the list of announcements
                        announcements.add(announcement);

                        break;
                    }
                }
            }

            for (Announcement announcement : announcements) {
                allAnnouncements.remove(announcement);
            }

            for (Announcement announcement : allAnnouncements) {
                // for each word in searchQuery
                for (String searchWord : searchWords) {
                    // if the announcement contains the word
                    if (announcement.getDescription().toLowerCase().contains(searchWord)) {
                        // add the announcement to the list of announcements
                        announcements.add(announcement);

                        break;
                    }
                }
            }

            for (Announcement announcement : announcements) {
                allAnnouncements.remove(announcement);
            }
        }

        if(searchQuery.isEmpty()) {
            announcements = allAnnouncements;
        }

        int firstIndex = Integer.parseInt(pageNumber) * Integer.parseInt(pageSize);
        int lastIndex = firstIndex + Integer.parseInt(pageSize);

        if (firstIndex > announcements.size()) {
            firstIndex = announcements.size();
        }

        if (lastIndex > announcements.size()) {
            lastIndex = announcements.size();
        }

        announcements = announcements.subList(firstIndex, lastIndex);

        System.out.println(((List) announcements).size());
        return announcements;
    }


    public void setAnnouncementsRepository(AnnouncementsDbRepository announcementsRepository) {
        this.announcementsRepository = announcementsRepository;
    }

    public Iterable getAnnouncementFields() {
        return announcementsRepository.findAllFields();
    }
}
>>>>>>> master
