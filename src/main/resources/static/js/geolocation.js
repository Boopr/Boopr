"use strict";

const successCallback = (position) => {
    console.log(position);
};

const errorCallback = (error) => {
    console.error(error);
}
const watchId = navigator.geolocation.watchPosition(successCallback, errorCallback);