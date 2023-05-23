package fys.fysmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.JOINED)
public class Reservation extends Identifiable<Integer>{
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
    @ManyToOne
    private User user;
    private LocalDate date;


    public Reservation() {}

    @JsonBackReference
    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
