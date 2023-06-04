package fys.fysserver.api.dtos.users;

public class RecentlyAnnouncementDto {
    private Integer announcementId;

    public RecentlyAnnouncementDto() {
    }

    public RecentlyAnnouncementDto(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }
}
