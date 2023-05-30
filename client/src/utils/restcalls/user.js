import {json, status} from '../utils.js';
import {FYS_BASE_URL} from "../constants";

export function login(loginForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let init = {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        mode: 'cors',
        body: JSON.stringify(loginForm),
    };
    let request = new Request(FYS_BASE_URL + "login", init);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data=> {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}

export function upgradeToSpecialist(profileForm) {
    let headers = new Headers();
    headers.append('Accept', 'application/json');

    let init = {
        method: 'PUT',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem('token'),
        },
        body: JSON.stringify(profileForm),
    };
    let request = new Request(FYS_BASE_URL + "user", init);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data=> {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}