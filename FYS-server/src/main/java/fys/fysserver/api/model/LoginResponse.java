package fys.fysserver.api.model;

import fys.fysmodel.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginResponse {
    private String token;
    private Integer id;
    private String username;
    private String password;
    private Collection<String> roles;

    public LoginResponse() {
    }

    public LoginResponse(String token, Integer id, String username, String password, Collection<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public Collection<String> getRoles() {
        return roles;
    }
}
