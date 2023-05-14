package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="visited_announcements")
@Inheritance(strategy = InheritanceType.JOINED)
public class VisitedAnnouncement extends Announcement {
    private LocalDateTime dateTime;

    public VisitedAnnouncement() {}

    public VisitedAnnouncement(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public VisitedAnnouncement(Float rate, String description, String title, LocalDate startDate, LocalDate endDate, LocalDateTime dateTime, Field field) {
        super(rate, description, title, startDate, endDate, field);
        this.dateTime = dateTime;
    }

    public VisitedAnnouncement(Integer integer, Float rate, String description, String title, LocalDate startDate, LocalDate endDate, LocalDateTime dateTime) {
        super(integer, rate, description, title, startDate, endDate);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
