export class SpecialistFormDto {
    description;
    location;
    constructor(description, location) {
        description = description || '';
        location = location || '';
    }
}