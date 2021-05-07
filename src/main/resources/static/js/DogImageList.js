import DogPanel from './DogPanel.js'

export default class DogImageList{

    constructor(element){

        this.element = document.getElementById(element);
        this.dogs = [];

        this.getDogs()
        return this;
    }
    
    getDogs(){
        let self = this;
        this.dogs = [];
        axios.get("/api/dogs").then( res =>{
            res.data.reverse();
            res.data.forEach( dog =>{
                self.dogs.push(new DogPanel(dog))
            })
            self.drawDogs()  
        })
    }

    drawDogs(){
        //clear area
        this.element.innerHTML = ""
        this.dogs.forEach( dog =>{
            this.element.appendChild(dog.element)
        })
    }

    


}