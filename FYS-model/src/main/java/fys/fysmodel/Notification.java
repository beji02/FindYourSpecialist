package fys.fysmodel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification extends Identifiable<Integer> {
    private String title;
    private String text;
    private LocalDateTime date;
    private boolean read;

    public Notification() {
        this.date = LocalDateTime.now();
        this.read = false;
    }

    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = LocalDateTime.now();
        this.read = false;
    }

    public Notification(Integer id, String title, String text, LocalDateTime date, boolean read) {
        super(id);
        this.title = title;
        this.text = text;
        this.date = date;
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
