package fys.fysserver.api.model;

import fys.fysmodel.Announcement;

public class AddAnnouncementResponse {
    private Announcement announcement;
    private Boolean isAddAnnouncementSuccess;
    private String errorMessage;

    public AddAnnouncementResponse(Announcement announcement, Boolean isAddAnnouncementSuccess, String errorMessage) {
        this.announcement = announcement;
        this.isAddAnnouncementSuccess = isAddAnnouncementSuccess;
        this.errorMessage = errorMessage;
    }
}
