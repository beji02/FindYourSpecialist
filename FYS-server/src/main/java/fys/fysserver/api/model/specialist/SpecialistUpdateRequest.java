package fys.fysserver.api.model.specialist;

public class SpecialistUpdateRequest {

    private String location;
    private String description;

    public SpecialistUpdateRequest() {
    }

    public SpecialistUpdateRequest(String location, String description) {
        this.location = location;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
