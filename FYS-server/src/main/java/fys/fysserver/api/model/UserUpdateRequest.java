package fys.fysserver.api.model;

import java.time.LocalDate;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String optionalDescription;

    private String email;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
        this.email = email;
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
}
