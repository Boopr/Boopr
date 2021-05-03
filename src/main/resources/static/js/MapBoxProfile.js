import apiKey from "/js/keys.js"

export default class MapBoxProfile{

    constructor(dog, element){
        mapboxgl.accessToken = apiKey;
        this.mapbox = new mapboxgl.Map({
            container: element,
            style: 'mapbox://styles/mapbox/streets-v11',
            center: [dog.lon, dog.lat],
            zoom: 10
        });
        this.marker = new mapboxgl.Marker({
            draggable: false,
            color: 'red',
        })
        .setLngLat([dog.lon, dog.lat])
        .addTo(this.mapbox);

    }

    updateLocation(dog){
        this.mapbox.flyTo({
            center:[dog.lon, dog.lat]
        })
    }
   
}
