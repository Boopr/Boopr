"use strict";

// Map
mapboxgl.accessToken = mapboxToken;
const coordinates = document.getElementById('coordinates');
const map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-98.4916, 29.4260],
    zoom: 13
});


const geocoder = new MapboxGeocoder({
    accessToken: mapboxgl.accessToken,
    mapboxgl: mapboxgl,
    marker: marker
});

document.getElementById('geocoder').appendChild(geocoder.onAdd(map));

// Marker
var marker = new mapboxgl.Marker({
    draggable: true,
    color: 'Steelblue',
})
    .setLngLat([-98.4916, 29.4260])
    .addTo(map);