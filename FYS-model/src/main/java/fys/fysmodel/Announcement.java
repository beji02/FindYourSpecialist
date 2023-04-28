package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="anouncements")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Announcement extends Identifiable<Integer> {
    private Float rate;
    private String description;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    public Announcement() {}

    public Announcement(Float rate, String description, String title, LocalDate startDate, LocalDate endDate) {
        this.rate = rate;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Announcement(Integer integer, Float rate, String description, String title, LocalDate startDate, LocalDate endDate) {
        super(integer);
        this.rate = rate;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
