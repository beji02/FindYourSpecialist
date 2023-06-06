package fys.fysserver.api.services;

import fys.fysmodel.*;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.dtos.*;
import fys.fysserver.api.dtos.announcements.*;
import fys.fysserver.api.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    /**
     * return true if a string contains a keyword from the list
     * @param str the string
     * @param keywords list of words
     * @return true if a string contains a keyword from the list
     */
    private Boolean containsOneOf(String str, List<String> keywords) {
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * filter the announcement list applying the query and the categories (word by word)
     * @param announcements list of announcements
     * @param searchQuery search query
     * @param searchCategories search categories
     * @return a new list of filtered announcements
     */
    private List<Announcement> filterAnnouncements(
            List<Announcement> announcements,
            String searchQuery,
            String searchCategories
    ) {
        if(!searchQuery.isEmpty()) {
            // split searchQuery and searchCategories into words
            List<String> keywords = new ArrayList<>(Arrays.stream(searchQuery.split(" ")).toList());
            List<Announcement> filteredAnnouncements = new ArrayList<>();

            // filter by query
            for (Announcement announcement : announcements) {
                if (containsOneOf(announcement.getTitle(), keywords) ||
                        containsOneOf(announcement.getDescription(), keywords)) {
                    filteredAnnouncements.add(announcement);
                }
            }

            announcements = filteredAnnouncements;
        }

        if(!searchCategories.isEmpty()) {
            List<String> keywords = new ArrayList<>(Arrays.stream(searchCategories.split(" ")).toList());
            List<Announcement> filteredAnnouncements = new ArrayList<>();

            // filter by field
            for (Announcement announcement : announcements) {
                for (String keyword : keywords) {
                    if (announcement.getField().getName().equals(keyword)) {
                        filteredAnnouncements.add(announcement);
                        break;
                    }
                }
            }

            announcements = filteredAnnouncements;
        }

        return announcements;
    }

    /**
     * get the specified page of announcements from the list
     * @param announcements list of announcements
     * @param pageNumber number of the page
     * @param pageSize size of the page
     * @return the page of announcements
     */
    private List<Announcement> paginateAnnouncements(
            List<Announcement> announcements,
            String pageNumber,
            String pageSize
    ) {
        int firstIndex = Integer.parseInt(pageNumber) * Integer.parseInt(pageSize);
        int lastIndex = firstIndex + Integer.parseInt(pageSize);

        if (firstIndex > announcements.size()) {
            firstIndex = announcements.size();
        }

        if (lastIndex > announcements.size()) {
            lastIndex = announcements.size();
        }

        return announcements.subList(firstIndex, lastIndex);
    }

    /**
     * get announcements of a specialist in DTO format;
     * announcements are filtered and paginated marked/not as favorite
     * @param username the username of the specialist
     * @param searchQuery search query
     * @param searchCategories search categories
     * @param pageNumber page number
     * @param pageSize page size
     * @return list of announcement DTOs
     * @throws ValidationException if username is not a specialist's username
     */
    public List<AnnouncementDto> getMyAnnouncements(
            String username,
            String searchQuery,
            String searchCategories,
            String pageNumber,
            String pageSize
    ) throws ValidationException
    {
        // get the specialist
        User user = usersRepository.findByUsername(username);
        if (!(user instanceof Specialist)) throw new ValidationException("username is invalid");
        Specialist specialist = (Specialist) user;

        // get selected announcements
        List<Announcement> announcements = specialist.getAnnouncements().stream().toList();
        announcements = filterAnnouncements(announcements, searchQuery, searchCategories);
        announcements = paginateAnnouncements(announcements, pageNumber, pageSize);

        // build the DTOs
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
                    Boolean isFavourite = specialist.getFavoriteAnnouncements().contains(announcement);
                    announcementDtos.add(DtoUtils.buildAnnouncementDTO(announcement, isFavourite));
                }
        );

        return announcementDtos;
    }

    /**
     * get all announcements in DTO format;
     * announcements are filtered, paginated and marked/not as favorite
     * @param username username of the user
     * @param searchQuery search query
     * @param searchCategories search categories
     * @param pageNumber page number
     * @param pageSize page size
     * @return list of announcement DTOs
     */
    public List<AnnouncementDto> getAnnouncements(
            String username,
            String searchQuery,
            String searchCategories,
            String pageNumber,
            String pageSize
    ) {
        // get user
        User user = usersRepository.findByUsername(username);

        // get selected announcements
        List<Announcement> announcements = (List<Announcement>) announcementsRepository.findAll();
        announcements = filterAnnouncements(announcements, searchQuery, searchCategories);
        announcements = paginateAnnouncements(announcements, pageNumber, pageSize);

        // build the DTOs
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
            Boolean isFavourite = (user != null) && user.getFavoriteAnnouncements().contains(announcement);
            announcementDtos.add(DtoUtils.buildAnnouncementDTO(announcement, isFavourite));
        });

        return announcementDtos;
    }

    /**
     * get a list with all fields in DTO format
     * @return list of fields
     */
    public List<FieldDto> getAnnouncementFields() {
        Iterable<Field> fields = announcementsRepository.findAllFields();

        List<FieldDto> fieldsDTO = new ArrayList<>();
        fields.forEach(field -> fieldsDTO.add(DtoUtils.buildFieldDTO(field)));

        return fieldsDTO;
    }

    /**
     * adds a new announcement to repository
     *
     * @param newAnnouncementDto dto format of the new announcement
     * @return the created announcement in dto format
     * @throws ValidationException if one of the following is false:
     * username is not a specialist's username;
     * fieldId is not a valid field id;
     */
    public AnnouncementDto addAnnouncement(
            String username,
            NewAnnouncementDto newAnnouncementDto
    ) throws ValidationException
    {
        // load details of newAnnouncementDto
        String title = newAnnouncementDto.getTitle();
        String description = newAnnouncementDto.getDescription();
        Integer fieldId = newAnnouncementDto.getFieldId();
        Set<LocalDate> workDays = newAnnouncementDto.getWorkDays();

        System.out.println("addAnnouncement: " + username + " " + title + " " + description + " " + fieldId + " " + workDays.toString());

        // get user
        User user = usersRepository.findByUsername(username);
        if (!(user instanceof Specialist)) throw new ValidationException("username is invalid");
        Specialist specialist = (Specialist) user;

        // get field
        Field field = announcementsRepository.findFieldById(fieldId);
        if (field == null) throw new ValidationException("fieldId is invalid");

        // create announcement
        Announcement announcement = new Announcement(title, description, field);

        // create the empty reservations
        Set<Reservation> reservations = new HashSet<>();
        workDays.forEach(day -> reservations.add(new Reservation(day)));

        // bound the entities both ways
        specialist.addAnnouncement(announcement);
        announcement.setSpecialist(specialist);
        reservations.forEach(reservation -> reservation.setAnnouncement(announcement));
        announcement.setReservations(reservations);

        // persist the entities (note that persisting the specialist automatically
        // persists announcements and reservations too)
        usersRepository.modify(specialist);

        // build the DTO
        AnnouncementDto announcementDto = DtoUtils.buildAnnouncementDTO(announcement, false);

        return announcementDto;
    }

    /**
     * add an announcement to favourites
     * @param username username of the user
     * @param announcementId id of the announcement
     * @throws ValidationException if username is not a user's username or
     * announcementId is not an annoucement's id
     */
    public void addAnnouncementToFavourites(
            String username,
            Integer announcementId
    ) throws ValidationException
    {
        System.out.println("addAnnouncementToFavourites: " + username + " " + announcementId);

        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("user is invalid");

        // get announcement
        Announcement announcement = announcementsRepository.findById(announcementId);
        if(announcement == null) throw new ValidationException("announcementId is invalid");

        // add announcement to favourites
        user.addFavouriteAnnouncement(announcement);
        usersRepository.modify(user);
    }

    /**
     * remove an announcement from favourites
     * @param username username of the user
     * @param announcementId id of announcement
     * @throws ValidationException if username is not a valid username or
     * announcementId is not a valid announcement id
     */
    public void removeAnnouncementFromFavourites(
            String username,
            Integer announcementId
    ) throws ValidationException
    {
        System.out.println("removeAnnouncementFromFavourites: " + username + " " + announcementId);

        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("username is invalid");

        // get announcement
        Announcement announcement = announcementsRepository.findById(announcementId);
        if(announcement == null) throw new ValidationException("announcementId is invalid");

        // remove announcement from favorite list
        user.removeFavouriteAnnouncement(announcement);
        usersRepository.modify(user);
    }

    /**
     * get favourite announcements of a user in DTO format
     * @param username username of the user
     * @return list of announcement DTOs
     * @throws ValidationException if username is not valid
     */
    public List<AnnouncementDto> getFavouriteAnnouncements(
            String username
    ) throws ValidationException
    {
        System.out.println("getFavouriteAnnouncements: " + username);

        // get user
        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("username is not valid");

        // get favorite announcements
        List<Announcement> favoriteAnnouncements = user.getFavoriteAnnouncements().stream().toList();

        // build the DTOs
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        favoriteAnnouncements.forEach(announcement -> {
            announcementDtos.add(DtoUtils.buildAnnouncementDTO(announcement, true));
        });

        return announcementDtos;
    }

    /**
     * make a reservation for a user
     * @param username username of the user
     * @param newReservationDTO reservation in DTO format
     * @throws ValidationException if username is invalid or
     * announcementId is invalid
     */
    public void addReservation(
            String username,
            NewReservationDto newReservationDTO
    ) throws ValidationException
    {
        // get user
        User user = usersRepository.findByUsername(username);
        if(user == null) throw new ValidationException("username is invalid");

        // get announcement
        Announcement announcement = announcementsRepository.findById(newReservationDTO.getAnnouncementId());
        if(announcement == null) throw new ValidationException("announcementId is invalid");

        // add the user to each valid reservation
        announcement.getReservations().forEach(reservation -> {
            newReservationDTO.getSelectedDates().forEach(selectedDate -> {
                if (reservation.getDate().equals(selectedDate)) {
                    reservation.setUser(user);
                }
            });
        });
        announcementsRepository.modify(announcement);
        addNotification(announcement.getSpecialist().getUsername(), user, newReservationDTO, announcement);
    }

    /**
     * add a notification to a specialist
     * @param specialistUsername username of the specialist
     * @param user user that made the reservation
     * @param newReservationDTO reservation in DTO format
     * @param announcement announcement that was reserved
     */
    private void addNotification(String specialistUsername, User user, NewReservationDto newReservationDTO, Announcement announcement) {
        Specialist specialist = (Specialist) usersRepository.findByUsername(specialistUsername);
        Notification notification = new Notification(
                "New reservation by " + user.getFirstName() + " " + user.getLastName(),
                "You have a new reservation for " + announcement.getTitle() + " on " + newReservationDTO.getSelectedDates().toString()
        );

        specialist.addNotification(notification);
        usersRepository.modify(specialist);
    }

    /**
     * get reservations of an announcement
     * @param announcementId id of the announcement
     * @return list of reservation DTOs
     * @throws ValidationException if announcementId is invalid
     */
    public List<ReservationDto> getAnnouncementReservations(Integer announcementId) throws ValidationException {
        // get announcement
        Announcement announcement = announcementsRepository.findById(announcementId);
        if(announcement == null) throw new ValidationException("announcementId is invalid");

        // get reservations
        List<Reservation> reservations = announcement.getReservations().stream().toList();

        // build the DTO
        List<ReservationDto> reservationDtos = new ArrayList<>();
        reservations.forEach(reservation -> {
            reservationDtos.add(DtoUtils.buildReservationDTO(reservation));
        });

        return reservationDtos;
    }


    /**
     * get specialist's scheduled future reservations, sorted ascending by date
     * @param username username of the specialist
     * @return list of reservation DTOs
     * @throws ValidationException if username is not a specialist's username
     */
    public List<ScheduledReservationDto> getMySchedule(String username) throws ValidationException {
        // get specialist
        User user = usersRepository.findByUsername(username);
        if (!(user instanceof Specialist)) throw new ValidationException("username is invalid");
        Specialist specialist = (Specialist) user;

        // get scheduled reservations
        List<Reservation> reservations = specialist.getAnnouncements().stream()
                .flatMap(announcement -> announcement.getReservations().stream())
                .filter(reservation -> reservation.getDate().isAfter(LocalDate.now().minusDays(1)))
                .filter(reservation -> reservation.getUser() != null)
                .sorted(Comparator.comparing(Reservation::getDate))
                .toList();

        // build the DTO
        List<ScheduledReservationDto> scheduledReservationDtos = new ArrayList<>();
        reservations.forEach(reservation -> {
            scheduledReservationDtos.add(DtoUtils.buildScheduledReservationDto(reservation));
        });

        return scheduledReservationDtos;
    }


    /**
     * delete a scheduled reservation (the date will no longer be available)
     * @param username username of the specialist
     * @param scheduleId id of the scheduled reservation
     * @throws ValidationException if username is not a specialist's username or
     * scheduleId is not a valid scheduled reservation id
     */
    public void deleteSchedule(String username, Integer scheduleId) throws ValidationException {
        // get specialist
        User user = usersRepository.findByUsername(username);
        if (!(user instanceof Specialist)) throw new ValidationException("username is invalid");
        Specialist specialist = (Specialist) user;

        // get scheduled reservation
        Reservation reservation = announcementsRepository.findReservationById(scheduleId);
        if (reservation == null) throw new ValidationException("scheduleId is invalid");

        // delete scheduled reservation
        announcementsRepository.deleteReservation(reservation);
    }

    /**
     * get user's reservations in DTO format
     * @param username username of the user
     * @return list of reservation DTOs
     * @throws ValidationException if username is invalid
     */
    public List<MyReservationDto> getMyReservations(String username) throws ValidationException {
        // get specialist
        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("username is invalid");

        // get my reservations
        List<Reservation> reservations = user.getMyReservations().stream()
                .filter(reservation -> reservation.getDate().isAfter(LocalDate.now().minusDays(1)))
                .filter(reservation -> reservation.getUser() != null)
                .sorted(Comparator.comparing(Reservation::getDate))
                .toList();

        // build the DTO
        List<MyReservationDto> myReservationDtos = new ArrayList<>();
        reservations.forEach(reservation -> {
            myReservationDtos.add(DtoUtils.buildMyReservationDto(reservation));
        });

        return myReservationDtos;
    }

    /**
     * delete a user's reservation (the date will be available for other users)
     * @param username username of the user
     * @param reservationId id of the reservation
     * @throws ValidationException if username is invalid or
     * reservationId is not a valid reservation id
     */
    public void deleteReservation(String username, Integer reservationId) throws ValidationException {
        // get user
        User user = usersRepository.findByUsername(username);
        if (user == null) throw new ValidationException("username is invalid");

        // get reservation
        Reservation reservation = user.getMyReservations().stream()
                .filter(reservation1 -> reservation1.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
        if (reservation == null) throw new ValidationException("reservationId is invalid");

        // delete reservation
        reservation.setUser(null);
        announcementsRepository.modifyReservation(reservation);
    }

    /**
     * delete one of specialist's announcements
     * @param username username of the specialist
     * @param announcementId id of the announcement
     * @throws ValidationException if username is not a specialist's username or
     * announcementId is not one of specialist's announcements
     */
    public void deleteAnnouncement(String username, Integer announcementId) throws ValidationException {
        // get specialist
        User user = usersRepository.findByUsername(username);
        if (!(user instanceof Specialist)) throw new ValidationException("username is invalid");
        Specialist specialist = (Specialist) user;

        // get announcement
        Announcement announcement = specialist.getAnnouncements().stream()
                .filter(announcement1 -> announcement1.getId().equals(announcementId))
                .findFirst()
                .orElse(null);
        if (announcement == null) throw new ValidationException("announcementId is invalid");

        // delete announcement
        announcementsRepository.remove(announcement);
    }

    /**
     * get announcement by id
     * @param id id of the announcement
     * @return announcement
     */
    public Announcement findAnnouncementById(Integer id) throws ValidationException {
        if(id == null || id <= 0) throw new ValidationException("id is invalid");
        return announcementsRepository.findById(id);
    }
}

