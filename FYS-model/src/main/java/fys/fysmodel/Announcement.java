package fys.fysmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

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

    public Announcement(Float rate, String description, String title, LocalDate startDate, LocalDate endDate) {
        super(null);
        this.rate = rate;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Announcement(String title, String description, Field field, Set<LocalDate> workDays) {
        super(null);
        this.title = title;
        this.description = description;
        this.field = field;
    }

    public Announcement(String title, String description, Field field) {
        this.title = title;
        this.description = description;
        this.field = field;
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

    @JsonBackReference
    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "rate=" + rate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reservations=" + reservations +
                ", specialist=" + specialist +
                ", field=" + field +
                '}';
    }
}
