package fys.fysserver.api.model;

public class AnnouncementFavouritesRequest {
    private Integer announcementId;

    public AnnouncementFavouritesRequest() {
    }

    public AnnouncementFavouritesRequest(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }
}
