package fys.fysserver.api.model;

public class AddAnnouncementToFavouritesRequest {
    private Integer announcementId;

    public AddAnnouncementToFavouritesRequest() {
    }

    public AddAnnouncementToFavouritesRequest(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }
}
