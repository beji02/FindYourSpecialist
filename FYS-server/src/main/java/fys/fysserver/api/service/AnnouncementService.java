package fys.fysserver.api.service;

import fys.fysmodel.*;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.dto.AnnouncementDTO;
import fys.fysserver.api.dto.ReservationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AnnouncementService {
    private AnnouncementsRepository announcementsRepository;
    private UsersRepository usersRepository;

    public void setAnnouncementsRepository(AnnouncementsRepository announcementsRepository) {
        this.announcementsRepository = announcementsRepository;
    }

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public AnnouncementService() {
    }

    public Iterable<AnnouncementDTO> getFilteredAnnouncementsWithFavourites(String username, String searchQuery, String searchCategories, String pageNumber, String pageSize) {
        List<Announcement> allAnnouncements = (List<Announcement>) getFilteredAnnouncements(searchQuery, searchCategories, pageNumber, pageSize);
        List<AnnouncementDTO> announcementsDto = new ArrayList<>();
        if (username != null) {
            User user = usersRepository.findByUsername(username);
            Set<Announcement> favourites = user.getFavoriteAnnouncements();

            allAnnouncements.forEach(announcement -> {
                boolean isFavourite = favourites.contains(announcement);
                announcementsDto.add(new AnnouncementDTO(announcement.getId(), announcement.getRate(),
                        announcement.getDescription(), announcement.getTitle(), null,
                        null, announcement.getReservations(), announcement.getField(), announcement.getSpecialist(), isFavourite));
            });
        } else {
            allAnnouncements.forEach(announcement -> announcementsDto.add(new AnnouncementDTO(announcement.getId(), announcement.getRate(),
                    announcement.getDescription(), announcement.getTitle(), null,
                    null, announcement.getReservations(), announcement.getField(), announcement.getSpecialist(), false)));
        }
        return announcementsDto;
    }

    public Iterable<Announcement> getFilteredAnnouncements(String searchQuery, String searchCategories, String pageNumber, String pageSize) {
        List<Announcement> allAnnouncements = (List<Announcement>) announcementsRepository.findAll();
        List<Announcement> announcements = new ArrayList<>();

        if (!searchCategories.isEmpty()) {
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

        if (searchQuery.isEmpty()) {
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

        System.out.println((announcements).size());
        return announcements;
    }

    public Iterable<AnnouncementDTO> getMyAnnouncements(String username, String searchQuery, String searchCategories, String pageNumber, String pageSize) {
        List<AnnouncementDTO> announcements = (List<AnnouncementDTO>) getFilteredAnnouncementsWithFavourites(username, searchQuery, searchCategories, pageNumber, pageSize);
        List<AnnouncementDTO> myAnnouncements = new ArrayList<>();
        announcements.forEach(
                announcement -> {
                    if (announcement.getSpecialist().getUsername().equals(username)) {
                        myAnnouncements.add(announcement);
                    }
                }
        );
        return myAnnouncements;
    }

    public Iterable<Field> getAnnouncementFields() {
        return announcementsRepository.findAllFields();
    }

    public Announcement addAnnouncement(String username, String title, String description, LocalDate startDate, LocalDate endDate, Integer fieldId, Set<LocalDate> workDays) {
        System.out.println("addAnnouncement: " + username + " " + title + " " + description + " " + fieldId + " " + workDays.toString());

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setStartDate(startDate);
        announcement.setEndDate(endDate);
        announcement.setRate(0f);
        announcement.setField(announcementsRepository.findFieldById(fieldId));

        Specialist specialist = (Specialist) usersRepository.findByUsername(username);
        announcement.setSpecialist(specialist);
        announcement.setReservations(new HashSet<>());

        Announcement addedAnnouncement = announcementsRepository.add(announcement);

        workDays.forEach(workDay -> {
            Reservation reservation = new Reservation();
            reservation.setDate(workDay);
            reservation.setAnnouncement(addedAnnouncement);
            reservation = announcementsRepository.insertReservation(reservation);

            addedAnnouncement.addReservation(reservation);
        });

        announcementsRepository.modify(addedAnnouncement);

        specialist.addAnnouncement(addedAnnouncement);
        usersRepository.modify(specialist);

        System.out.println("addedAnnouncement: " + addedAnnouncement.toString());
        return addedAnnouncement;
    }

    public void addAnnouncementToFavourites(String username, Integer announcementId) {
        System.out.println("addAnnouncementToFavourites: " + username + " " + announcementId);
        User user = usersRepository.findByUsername(username);
        Announcement announcement = announcementsRepository.findById(announcementId);
        System.out.println("addAnnouncementToFavourites: " + user.toString() + " " + announcement.toString());
        user.addFavouriteAnnouncement(announcement);
        usersRepository.modify(user);
    }

    public void removeAnnouncementFromFavourites(String username, Integer announcementId) {
        System.out.println("deleteAnnouncementFromFavourites: " + username + " " + announcementId);
        User user = usersRepository.findByUsername(username);
        Announcement announcement = announcementsRepository.findById(announcementId);
        System.out.println("deleteAnnouncementFromFavourites: " + user.toString() + " " + announcement.toString());
        user.removeFavouriteAnnouncement(announcement);
        usersRepository.modify(user);
    }

    public Iterable<Announcement> getFavouriteAnnouncements(String username) {
        System.out.println("getFavouriteAnnouncements: " + username);
        User user = usersRepository.findByUsername(username);
        System.out.println("getFavouriteAnnouncements: " + user.toString());
        return user.getFavoriteAnnouncements();
    }

    public void addReservation(String username, ReservationDTO reservationDTO) {
        System.out.println("addReservation: " + username + " " + reservationDTO.toString());
        User user = usersRepository.findByUsername(username);

        Integer announcementId = reservationDTO.getAnnouncementId();
        Announcement announcement = announcementsRepository.findById(announcementId);

        if (user.getId().equals(announcement.getSpecialist().getId())) {
            throw new RuntimeException("You can't reserve your own announcement!");
        }

        if (user != null && announcement != null) {
            announcement.getReservations().forEach(reservation -> {
                reservationDTO.getSelectedDates().forEach(selectedDate -> {
                    if (reservation.getDate().equals(selectedDate)) {
                        reservation.setUser(user);
                    }
                });
            });
            announcementsRepository.modify(announcement);
        }
    }
}

