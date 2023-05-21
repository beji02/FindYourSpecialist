export class ProfileFormDto {
    firstName;
    lastName;
    username;
    phoneNumber;
    email;
    constructor(firstName, lastName, username, phoneNumber, email) {
        this.firstName = firstName || '';
        this.lastName = lastName || '';
        this.username = username || '';
        this.phoneNumber = phoneNumber || '';
        this.email = email || '';
    }
}