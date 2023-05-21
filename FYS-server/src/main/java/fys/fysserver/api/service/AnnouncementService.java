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
