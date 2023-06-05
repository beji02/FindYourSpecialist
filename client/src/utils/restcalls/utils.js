import {json, status} from "../utils";

export function processRequest(request) {
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

export function processEmptyRequest(request) {
    return fetch(request)
        .then(status)
        .then(data => {return data})
        .catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}

export function createRequest(data, method) {
    console.log(JSON.stringify(data));
    return {
        method: method,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem('token'),
        },
        mode: 'cors',
        body: JSON.stringify(data),
    };
}