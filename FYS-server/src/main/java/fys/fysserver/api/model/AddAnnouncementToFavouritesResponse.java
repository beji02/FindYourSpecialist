package fys.fysserver.api.model;

public class AddAnnouncementToFavouritesResponse {
    private boolean success;
    private String message;

    public AddAnnouncementToFavouritesResponse() {
    }

    public AddAnnouncementToFavouritesResponse(boolean success, String message) {
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
