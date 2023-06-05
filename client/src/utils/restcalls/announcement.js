import {FYS_BASE_URL} from "../constants";
import {createRequest, processEmptyRequest, processRequest} from "./utils";

export function getFields() {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(undefined, "GET");
    let request = new Request(FYS_BASE_URL + "fields", init);
    return processRequest(request);
}

export function getAnnouncements(searchQuery, pageNumber, pageSize, filtersQueryParam) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(undefined, "GET");
    let request = new Request(FYS_BASE_URL + `announcements?search-query=${searchQuery}&page-number=${pageNumber}&page-size=${pageSize}&search-categories=${filtersQueryParam}`, init);
    return processRequest(request);
}

export function getMyAnnouncements(searchQuery, pageNumber, pageSize, filtersQueryParam) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = createRequest(undefined, "GET");
    let request = new Request(FYS_BASE_URL + `my-announcements?search-query=${searchQuery}&page-number=${pageNumber}&page-size=${pageSize}&search-categories=${filtersQueryParam}`, init);
    return processRequest(request);
}

export function createReservation(reservation) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');

    let init = createRequest(reservation, "POST");
    let request = new Request(FYS_BASE_URL + "reservations", init);
    return processEmptyRequest(request);
}