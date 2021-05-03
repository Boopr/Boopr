import apiKey from "./keys.js"

export default class MapBoxForm{

    /**
     * 
     * @param {String} apiKey Input the API key for mapbox
     * @param {String} height (Optional) Specify the height of the mapbox container
     * @param {Object} options (Optional) Specify mapbox options, passes though to the api;
     */
    constructor( height = "300px" , options){

        this.element = document.createElement("div");
        this.element.id = "map"
        this.element.setAttribute("class","w-100 mb-2")
        this.element.style.height = height;
        this.element.style.width = "100%";

        mapboxgl.accessToken = apiKey;

        this.values = options || {
            center:[
                -98.67820716986465,
                30.426650757266202
            ],
            style: 'mapbox://styles/mapbox/streets-v11', // style URL
            container: this.element,
            zoom: 3
        }
        this.map = new mapboxgl.Map(this.values);
        this.markers = new Map();
        this.hidden = document.createElement("input");
        this.hidden.type = "hidden";
        this.hidden.id = "mapbox-value"
        this.currentMarker = null;

        this.map.on('load', ()=> {
            this.map.resize();
        });
        
    }

    export(){
        return this.element;
    }

    exportForm(){
        return this.hidden;
    }

    addMarker(id){
        let marker = new mapboxgl.Marker({
            draggable: true   
        })
        marker.setLngLat(this.map.getCenter()).addTo(this.map);
        marker.on('drag', (e)=>{
            this._call();
        })
        this.markers.set(id,marker);
        this.currentMarker = id;
        this._call()
        return this.markers.get(id);
    }

    selectMarker(id){
        this.currentMarker = id;
        this.arr = [];
        this.arr[0] = this.markers.get(id).getLngLat().lng;
        this.arr[1] = this.markers.get(id).getLngLat().lat;
        this.hidden.value = this.arr
    }

    _call(){
        if(this.markers.size > 0){
            this.arr = [];
            this.arr[0] = this.markers.get(this.currentMarker).getLngLat().lng;
            this.arr[1] = this.markers.get(this.currentMarker).getLngLat().lat;
            this.hidden.value = this.arr
        }
    }

}
