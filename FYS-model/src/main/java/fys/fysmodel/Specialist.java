package fys.fysmodel;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="specialists")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Specialist extends User {
    private Integer area;
    private String location;
    private String description;

    public Specialist() {}

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rating> ratingsSpecialist = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Field> fields = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<>();

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

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void addRating(Rating rating) {
        this.ratingsSpecialist.add(rating);
    }

    public void removeRating(Rating rating) {
        this.ratingsSpecialist.remove(rating);
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public void removeField(Field field) {
        this.fields.remove(field);
    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
    }

    public void removeAnnouncement(Announcement announcement) {
        this.announcements.remove(announcement);
    }
}
