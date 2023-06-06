package fys.fysserver.api.dtos.users;

import fys.fysmodel.Specialist;

public class SpecialistDto {
    private String location;
    private String description;

    public SpecialistDto() {
    }

    public SpecialistDto(Specialist specialist){
        this.location = specialist.getLocation();
        this.description = specialist.getDescription();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
