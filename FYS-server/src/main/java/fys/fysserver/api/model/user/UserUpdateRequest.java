package fys.fysserver.api.model.user;

import java.time.LocalDate;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String optionalDescription;

    private String email;
    private UpdateType updateType;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription, String email, UpdateType updateType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
        this.email = email;
        this.updateType = updateType;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }
}
