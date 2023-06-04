import {json, status} from '../utils.js';
import {FYS_BASE_URL} from "../constants";
import {createRequest, processRequest} from "./utils";

export function login(loginForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(loginForm, "POST");
    let request = new Request(FYS_BASE_URL + "login", init);
    return processRequest(request);
}

export function register(registerForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(registerForm, "POST");
    let request = new Request(FYS_BASE_URL + "register", init);
    return processRequest(request);
}

export function upgradeToSpecialist(profileForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(profileForm, "POST");
    let request = new Request(FYS_BASE_URL + "specialists", init);
    return processRequest(request);
}

export function updatePersonalInfo(personalForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(personalForm, "PUT");
    let request = new Request(FYS_BASE_URL + "users", init);
    return processRequest(request);
}

export function getPersonalInfo() {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(undefined, "GET");
    let request = new Request(FYS_BASE_URL + "users", init);
    return processRequest(request);
}

export function updateWorkInfo(workForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(workForm, "PUT");
    let request = new Request(FYS_BASE_URL + "specialists", init);
    return processRequest(request);
}

export function getWorkInfo() {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(undefined, "GET");
    let request = new Request(FYS_BASE_URL + "specialists", init);
    return processRequest(request);
}

export function addRecentlyVisitedAnnouncement(announcementId) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(null, "POST");
    let request = new Request(FYS_BASE_URL + "users/recently-visited-announcements/" + announcementId, init);
    return processRequest(request);
}

export function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
}