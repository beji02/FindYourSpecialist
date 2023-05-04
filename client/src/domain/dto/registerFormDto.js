export class RegisterFormDto {
    firstName;
    username;
    password;
    constructor(firstName, username, password) {
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }
}