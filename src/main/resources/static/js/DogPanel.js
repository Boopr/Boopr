import HoverImage from './HoverImage.js';
import apiKey from './keys.js'

export default class DogPanel{

    constructor(dog){
        this.dog = dog;
        let height = "300px";
        let width = "300px"
        this.element = document.createElement("div")
        this.image = new HoverImage(dog.images[0].url, "/profile/" + dog.id, width , height);
        this.element.setAttribute("class","m-2 border rounded")

        this.overlay = document.createElement("div");
        this.overlay.style.position = "relative"
        this.overlay.setAttribute("class","p-2 text-outline text-white d-flex justify-content-between")
        this.overlay.style.top = `-${height}`
        this.overlay.style.marginBottom = `-${this.overlay.height}`
        
        this.boops = document.createElement("h5");
        this.boops.innerHTML = "Total Boops : " + dog.allBoops

        this.sex = document.createElement("h5");
        this.sex.setAttribute("class","px-2 py-0 btn")
        if(dog.sex){
            this.sex.classList.add("btn-male")
            this.sex.innerHTML = `<i class="fas fa-mars"></i>`
        }else{
            this.sex.classList.add("btn-female")
            this.sex.innerHTML = `<i class="fas fa-venus"></i>`
        }
        
        this.overlay.appendChild(this.boops)
        this.overlay.appendChild(this.sex)

        this.bottom = document.createElement("div");
        this.bottom.setAttribute("class","row p-2 d-flex flex-column")
        this.bottom.style.position = "relative"
        this.bottom.style.top = `-50px`

        this.dogName = document.createElement("h4");
        this.dogName.setAttribute("class","")
        this.dogName.style.width = "300px"
        this.dogName.innerHTML = dog.name;

        this.dogLocation = document.createElement("div");
        this.dogLocation.setAttribute("class","")
        this.dogLocation.style.width = "300px"
        

        this.bottom.appendChild(this.dogName);
        this.bottom.appendChild(this.dogLocation)
        this.bottom.style.marginBottom = "-50px"

        this.element.appendChild(this.image.export);
        this.element.appendChild(this.overlay);
        this.element.appendChild(this.bottom);
        
        this.getLocation();
        return this;
    }

    export(){
        return this.element;
    }

    getLocation(){
        let endpoint = "mapbox.places"
        let dog = this.dog;
        let self = this;
        axios.get("https://api.mapbox.com" + `/geocoding/v5/${endpoint}/${dog.lon},${dog.lat}.json` + "?access_token=" + apiKey).then( res=>{
            //console.log(res.data.features[4].place_name)
            self.dogLocation.innerHTML = res.data.features[4].place_name
        })
    }

    
}