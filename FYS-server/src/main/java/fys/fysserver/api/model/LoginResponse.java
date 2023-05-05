package fys.fysserver.api.model;

import fys.fysmodel.User;

public class LoginResponse {
    private User user;
    private Boolean isLoginSuccess;
    private String errorMessage;

    public LoginResponse() {
    }

    public LoginResponse(User user, Boolean isLoginSuccess, String errorMessage) {
        this.user = user;
        this.isLoginSuccess = isLoginSuccess;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public User getUser() {
        return user;
    }

    public Boolean getLoginSuccess() {
        return isLoginSuccess;
    }
}
