package fys.fysserver.api.dtos.users;

import java.time.LocalDateTime;

public class NotificationDto {
    private Integer id;
    private String title;
    private String text;
    private LocalDateTime date;
    private boolean read;

    public NotificationDto() {
        this.date = LocalDateTime.now();
        this.read = false;
    }

    public NotificationDto(Integer id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = LocalDateTime.now();
        this.read = false;
    }

    public NotificationDto(fys.fysmodel.Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.date = notification.getDate();
        this.read = notification.isRead();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
