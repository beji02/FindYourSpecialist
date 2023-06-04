export function isSpecialist(roles) {
    return roles != undefined && roles.includes("ROLE_SPECIALIST");
}

export function isUser(roles) {
    return roles != undefined && roles.includes("ROLE_USER");
}