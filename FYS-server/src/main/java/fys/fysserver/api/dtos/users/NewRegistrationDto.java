package fys.fysserver.api.dtos.users;

public class NewRegistrationDto {
    private String username;
    private String password;
    private String email;
    public NewRegistrationDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public NewRegistrationDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

