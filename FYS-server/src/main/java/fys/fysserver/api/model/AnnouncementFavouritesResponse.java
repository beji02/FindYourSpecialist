package fys.fysserver.api.model;

public class AnnouncementFavouritesResponse {
    private boolean success;
    private String message;

    public AnnouncementFavouritesResponse() {
    }

    public AnnouncementFavouritesResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
