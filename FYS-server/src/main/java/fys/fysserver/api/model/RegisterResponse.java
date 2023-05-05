package fys.fysserver.api.model;

import fys.fysmodel.User;

public class RegisterResponse {
    private User user;
    private Boolean isRegisterSuccess;
    private String errorMessage;

    public RegisterResponse() {
    }

    public RegisterResponse(User user, Boolean isRegisterSuccess, String errorMessage) {
        this.user = user;
        this.isRegisterSuccess = isRegisterSuccess;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public User getUser() {
        return user;
    }

    public Boolean getRegisterSuccess() {
        return isRegisterSuccess;
    }
}
