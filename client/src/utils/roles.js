export function isSpecialist(roles) {
    return roles.includes("ROLE_SPECIALIST");
}

export function isUser(roles) {
    return roles.includes("ROLE_USER");
}