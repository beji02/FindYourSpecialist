export class RegisterFormDto {
    username;
    password;
    email;
    constructor(username, password, email) {
        this.username = username || '';
        this.password = password || '';
        this.email = email || '';
    }
}