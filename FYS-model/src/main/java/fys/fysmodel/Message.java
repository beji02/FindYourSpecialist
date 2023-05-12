package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="messages")
@Inheritance(strategy = InheritanceType.JOINED)
public class Message extends Identifiable<Integer> {
    private LocalDateTime dateTime;
    private String text;

    public Message() {}

    public Message(LocalDateTime dateTime, String text) {
        this.dateTime = dateTime;
        this.text = text;
    }

    public Message(Integer integer, LocalDateTime dateTime, String text) {
        super(integer);
        this.dateTime = dateTime;
        this.text = text;
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
}
