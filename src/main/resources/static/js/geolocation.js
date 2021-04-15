"use strict";

const successCallback = (position) => {
    console.log(position);
};

const errorCallback = (error) => {
    console.error(error);
}
navigator.geolocation.getCurrentPosition(successCallback, errorCallback);