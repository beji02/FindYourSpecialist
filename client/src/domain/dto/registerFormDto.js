export class RegisterFormDto {
    firstName;
    username;
    password;
    lastName;
    birthDate;
    phoneNumber;
    constructor(firstName, lastName, username, password, birthDate, phoneNumber) {
        this.firstName = firstName || '';
        this.lastName = lastName || '';
        this.username = username || '';
        this.password = password || '';
        this.birthDate = birthDate || '';
        this.phoneNumber = phoneNumber || '';
    }
}