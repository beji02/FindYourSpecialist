package fys.fysmodel;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="messages")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message extends Identifiable<Integer> {
    private LocalDateTime dateTime;
    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Specialist specialist;

    public Message() {}

    public Message(LocalDateTime dateTime, String text, User user, Specialist specialist) {
        this.dateTime = dateTime;
        this.text = text;
        this.user = user;
        this.specialist = specialist;
    }

    public Message(Integer integer, LocalDateTime dateTime, String text, User user, Specialist specialist) {
        super(integer);
        this.dateTime = dateTime;
        this.text = text;
        this.user = user;
        this.specialist = specialist;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
