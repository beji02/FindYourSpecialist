package fys.fysmodel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends Identifiable<Integer> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String optionalDescription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rating> ratingsUser = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Specialist> recentlyVisitedSpecialists = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Specialist> favoriteSpecialists = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> recentlyVisitedAnnouncements = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> favoriteAnnouncements = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    public User() {}

    public User(String username, String password, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public Set<Rating> getRatingsUser() {
        return ratingsUser;
    }

    public void setRatingsUser(Set<Rating> ratingsUser) {
        this.ratingsUser = ratingsUser;
    }

    public Set<Specialist> getRecentlyVisitedSpecialists() {
        return recentlyVisitedSpecialists;
    }

    public void setRecentlyVisitedSpecialists(Set<Specialist> recentlyVisitedSpecialists) {
        this.recentlyVisitedSpecialists = recentlyVisitedSpecialists;
    }

    public Set<Specialist> getFavoriteSpecialists() {
        return favoriteSpecialists;
    }

    public void setFavoriteSpecialists(Set<Specialist> favoriteSpecialists) {
        this.favoriteSpecialists = favoriteSpecialists;
    }

    public Set<Announcement> getRecentlyVisitedAnnouncements() {
        return recentlyVisitedAnnouncements;
    }

    public void setRecentlyVisitedAnnouncements(Set<Announcement> recentlyVisitedAnnouncements) {
        this.recentlyVisitedAnnouncements = recentlyVisitedAnnouncements;
    }

    public Set<Announcement> getFavoriteAnnouncements() {
        return favoriteAnnouncements;
    }

    public void setFavoriteAnnouncements(Set<Announcement> favoriteAnnouncements) {
        this.favoriteAnnouncements = favoriteAnnouncements;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
