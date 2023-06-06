package fys.fysserver.api.dtos.users;

import java.util.Collection;

public class LoginDto {
    private String token;
    private Integer id;
    private String username;
    private Collection<String> roles;

    public LoginDto() {
    }

    public LoginDto(String token, Integer id, String username, String password, Collection<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Collection<String> getRoles() {
        return roles;
    }
}
