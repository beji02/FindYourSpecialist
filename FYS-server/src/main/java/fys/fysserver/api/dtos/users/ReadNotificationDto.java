package fys.fysserver.api.dtos.users;

public class ReadNotificationDto {
    private Integer notificationId;

    public ReadNotificationDto() {
    }

    public ReadNotificationDto(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }
}
