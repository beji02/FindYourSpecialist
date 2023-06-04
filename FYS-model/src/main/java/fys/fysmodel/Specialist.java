package fys.fysmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="specialists")
@Inheritance(strategy = InheritanceType.JOINED)
public class Specialist extends User {
    private Integer area;
    private String location;
    private String description;

    public Specialist() {}

    public static Specialist build(User user) {
        Specialist specialist = new Specialist(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getPhoneNumber(), user.getOptionalDescription(), 0, "", "");
        specialist.setRecentlyVisitedAnnouncements(user.getRecentlyVisitedAnnouncementsMap());

        return specialist;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rating> ratingsSpecialist = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    public Specialist(Integer area, String location, String description) {
        this.area = area;
        this.location = location;
        this.description = description;
    }

    public Specialist(String username, String password, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription, Integer area, String location, String description) {
        super(username, password, firstName, lastName, birthDate, phoneNumber, optionalDescription);
        this.area = area;
        this.location = location;
        this.description = description;
    }

    public Specialist(String username, String password, String email, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription, Integer area, String location, String description) {
        super(username, password, email, firstName, lastName, birthDate, phoneNumber, optionalDescription);
        this.area = area;
        this.location = location;
        this.description = description;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
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

    public Set<Rating> getRatingsSpecialist() {
        return ratingsSpecialist;
    }

    public void setRatingsSpecialist(Set<Rating> ratingsSpecialist) {
        this.ratingsSpecialist = ratingsSpecialist;
    }

    @JsonBackReference
    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public Set<Message> getMessages() {
        return messages;
    }

    @Override
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addRating(Rating rating) {
        this.ratingsSpecialist.add(rating);
    }

    public void removeRating(Rating rating) {
        this.ratingsSpecialist.remove(rating);
    }


    @Transactional
    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
    }

    public void removeAnnouncement(Announcement announcement) {
        this.announcements.remove(announcement);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
    }

    public Float getRating() {
        Float rating = 0f;
        for (Rating r : this.ratingsSpecialist) {
            rating += r.getScore();
        }
        return rating / this.ratingsSpecialist.size();
    }
}
