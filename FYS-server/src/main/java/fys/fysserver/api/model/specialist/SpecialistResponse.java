package fys.fysserver.api.model.specialist;

import fys.fysmodel.*;
import fys.fysserver.api.model.user.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SpecialistResponse {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String optionalDescription;

    private String email;
    private String location;
    private String description;

    private Set<Rating> ratingsUser = new HashSet<>();

    private Iterable<Announcement> recentlyVisitedAnnouncements = new ArrayList<>();

    private Set<Specialist> favoriteSpecialists = new HashSet<>();
    private Set<Announcement> favoriteAnnouncements = new HashSet<>();
    private Set<Message> messages = new HashSet<>();

    public SpecialistResponse() {}

    public SpecialistResponse(String username, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
    }

    public SpecialistResponse(String username, String firstName, String lastName,
                              LocalDate birthDate, String phoneNumber, String optionalDescription, String email,
                              String location, String description,
                              Set<Rating> ratingsUser, Iterable<Announcement> recentlyVisitedAnnouncements,
                              Set<Specialist> favoriteSpecialists, Set<Announcement> favoriteAnnouncements,
                              Set<Message> messages) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
        this.email = email;
        this.location = location;
        this.description = description;
        this.ratingsUser = ratingsUser;
        this.recentlyVisitedAnnouncements = recentlyVisitedAnnouncements;
        this.favoriteSpecialists = favoriteSpecialists;
        this.favoriteAnnouncements = favoriteAnnouncements;
        this.messages = messages;
    }

    public static SpecialistResponse build(Specialist specialist) {
        return new SpecialistResponse(specialist.getUsername(), specialist.getFirstName(), specialist.getLastName(), specialist.getBirthDate(),
                specialist.getPhoneNumber(), specialist.getOptionalDescription(), specialist.getEmail(), specialist.getLocation(),
                specialist.getDescription(), specialist.getRatingsUser(),
                specialist.getRecentlyVisitedAnnouncements(), specialist.getFavoriteSpecialists(),
                specialist.getFavoriteAnnouncements(), specialist.getMessages());
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getOptionalDescription() {
        return optionalDescription;
    }

    public Set<Rating> getRatingsUser() {
        return ratingsUser;
    }

    public Set<Specialist> getFavoriteSpecialists() {
        return favoriteSpecialists;
    }


    public Set<Announcement> getFavoriteAnnouncements() {
        return favoriteAnnouncements;
    }


    public Set<Message> getMessages() {
        return messages;
    }


    public void addFavouriteSpecialist(Specialist specialist) {
        this.favoriteSpecialists.add(specialist);
    }


    public void addFavouriteAnnouncement(Announcement announcement) {
        this.favoriteAnnouncements.add(announcement);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public void setRatingsUser(Set<Rating> ratingsUser) {
        this.ratingsUser = ratingsUser;
    }

    public void setRecentlyVisitedAnnouncements(Iterable<Announcement> recentlyVisitedAnnouncements) {
        this.recentlyVisitedAnnouncements = recentlyVisitedAnnouncements;
    }

    public void setFavoriteSpecialists(Set<Specialist> favoriteSpecialists) {
        this.favoriteSpecialists = favoriteSpecialists;
    }

    public void setFavoriteAnnouncements(Set<Announcement> favoriteAnnouncements) {
        this.favoriteAnnouncements = favoriteAnnouncements;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Iterable<Announcement> getRecentlyVisitedAnnouncements() {
        return recentlyVisitedAnnouncements;
    }
}
