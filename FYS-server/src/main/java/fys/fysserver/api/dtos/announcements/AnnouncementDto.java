package fys.fysserver.api.dtos.announcements;

import fys.fysmodel.Announcement;

public class AnnouncementDto {
    private Integer id;
    private String title;
    private String description;
    private Boolean isFavorite;
    private String specialistName;
    private Float specialistRating;
    private String specialistPhoneNumber;
    private String specialistLocation;
    private String specialistUsername;

    public AnnouncementDto(Announcement announcement, Boolean isFavorite) {
        this.id = announcement.getId();
        this.title = announcement.getTitle();
        this.description = announcement.getDescription();
        this.isFavorite = isFavorite;
        this.specialistName = announcement.getSpecialist().getUser().getFirstName() + " " + announcement.getSpecialist().getUser().getLastName();
        this.specialistRating = announcement.getSpecialist().getRating();
        this.specialistPhoneNumber = announcement.getSpecialist().getUser().getPhoneNumber();
        this.specialistLocation = announcement.getSpecialist().getLocation();
        this.specialistUsername = announcement.getSpecialist().getUser().getUsername();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public Float getSpecialistRating() {
        return specialistRating;
    }

    public String getSpecialistPhoneNumber() {
        return specialistPhoneNumber;
    }

    public void setSpecialistPhoneNumber(String specialistPhoneNumber) {
        this.specialistPhoneNumber = specialistPhoneNumber;
    }

    public String getSpecialistLocation() {
        return specialistLocation;
    }

    public void setSpecialistLocation(String specialistLocation) {
        this.specialistLocation = specialistLocation;
    }

    public String getSpecialistUsername() {
        return specialistUsername;
    }

    public void setSpecialistUsername(String specialistUsername) {
        this.specialistUsername = specialistUsername;
    }
}
