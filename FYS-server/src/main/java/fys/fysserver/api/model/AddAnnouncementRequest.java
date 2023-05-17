package fys.fysserver.api.model;

import java.time.LocalDate;

public class AddAnnouncementRequest {
    private String token;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


    public AddAnnouncementRequest() {
    }

    public AddAnnouncementRequest(String token, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.token = token;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
