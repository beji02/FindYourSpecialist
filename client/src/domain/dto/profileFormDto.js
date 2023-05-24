export class ProfileFormDto {
    firstName;
    lastName;
    username;
    phoneNumber;
    email;
    updateType;
    constructor(firstName, lastName, username, phoneNumber, email, updateType) {
        this.firstName = firstName || '';
        this.lastName = lastName || '';
        this.username = username || '';
        this.phoneNumber = phoneNumber || '';
        this.email = email || '';
        this.updateType = updateType || '';
    }
}