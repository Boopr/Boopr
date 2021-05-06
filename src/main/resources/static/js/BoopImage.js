import HoverImage from "./HoverImage.js"

export default class BoopImage {

    constructor(image){

        this.id = image.id;
        this.image = new HoverImage(image.url);

        this.element = document.createElement("div");
        this.element.appendChild(this.image.export);
        this.element.style.width = "300px"
        this.element.style.height = "360px"
        this.element.setAttribute("class","border rounded m-2");

        this.overlay = document.createElement("div");
        this.overlay.style.marginBottom = "300px";
        this.overlay.style.position = "relative"
        this.overlay.style.top = "-360px";
        this.overlay.style.left = "50px"
        this.overlay.style.zIndex = "20";
        this.overlay.style.visibility = "hidden"
        this.overlay.style.fontSize = "12em"
        this.overlay.innerHTML = `<i class="fas fa-paw"></i>`

        this.bottomText = document.createElement("div");
        this.bottomText.setAttribute("class","d-flex align-items-center justify-content-between m-auto p-3")

        this.status = document.createElement("div");
        
        this.status.setAttribute("class","")

        this.boopStatus = image.booped
        this.boopStatusChanger();
        
        this.counter = document.createElement("div");
        this.counter.innerHTML = "Boops: " + 0
        this.counter.setAttribute("class","flex-fill")

        this.bottomText.appendChild(this.counter);
        this.bottomText.appendChild(this.status);
        
        this.element.appendChild(this.bottomText);
        this.element.appendChild(this.overlay);
        
        

    }

    boopStatusChanger(){
        if(this.boopStatus == "true"){
            this.status.innerHTML = `Booped <i class="fas fa-paw btn btn-warning"></i>`
            this.boopStatus = "false"
        }else if(this.boopStatus == "false"){
            this.status.innerHTML = `Boop me! <i class="fas fa-paw btn btn-danger"></i> `
            this.boopStatus = "true"
        }

    }

   

    updateCounter(total){
        this.counter.innerHTML = "Boops: " + total
    }

    boopAction(callback){
        let self = this;
        this.image.export.addEventListener( 'click', ()=>{          
            self.boopStatusChanger();
            self.animate()
            console.log("boop!")
            callback();
        })

        this.status.addEventListener('click', ()=>{
            self.boopStatusChanger();
            self.animate()
            console.log("boop!")
            callback();
        })

        this.image.export.addEventListener('touchmove', (e)=>{
            
        })
    }

    animate(){
        this.overlay.classList.toggle("boopAnimate")
        this.overlay.addEventListener("animationend", ()=>{
            this.overlay.classList.remove("boopAnimate")
        });
    }

    export(){
        return this.element;
    }

    draw(element){
        document.getElementById(element).appendChild(this.image);
    }

}
