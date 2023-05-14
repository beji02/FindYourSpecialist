package fys.fysmodel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="announcements")
@Inheritance(strategy = InheritanceType.JOINED)
public class Announcement extends Identifiable<Integer> {
    private Float rate;
    private String description;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    public Announcement() {}

    public Announcement(Float rate, String description, String title, LocalDate startDate, LocalDate endDate, Field field) {
        this.rate = rate;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.field = field;
    }

    public Announcement(Integer integer, Float rate, String description, String title, LocalDate startDate, LocalDate endDate) {
        super(integer);
        this.rate = rate;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
