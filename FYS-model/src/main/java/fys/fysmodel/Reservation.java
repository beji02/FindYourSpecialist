package fys.fysmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.JOINED)
public class Reservation extends Identifiable<Integer> {
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;

    public Reservation() {
    }

    public Reservation(LocalDate date) {
        super(null);
        this.date = date;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(announcement, that.announcement)) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (announcement != null ? announcement.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
