package fys.fysserver.api.dtos.users;

public class NewLoginDto {
    private String username;
    private String password;

    public NewLoginDto() {
    }

    public NewLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
