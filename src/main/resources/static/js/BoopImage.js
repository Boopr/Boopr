import HoverImage from "./HoverImage.js"

export default class BoopImage {

    constructor(image){

        this.id = image.id;
        this.image = new HoverImage(image.url);

        this.element = document.createElement("div");
        this.element.appendChild(this.image.export);
        this.element.setAttribute("class","border rounded");

        this.overlay = document.createElement("div");
        this.overlay.style.marginBottom = "-300px";
        this.overlay.style.position = "relative"
        this.overlay.style.top = "-300px";
        this.overlay.style.left = "50px"
        this.overlay.style.zIndex = "20";
        this.overlay.style.visibility = "hidden"
        this.overlay.style.fontSize = "12em"
        this.overlay.innerHTML = `<i class="fas fa-paw"></i>`

        this.counter = document.createElement("div");
        this.counter.innerHTML = "Boops: " + 0
        this.counter.setAttribute("class","px-2 py-3")

        this.element.appendChild(this.overlay);
        this.element.appendChild(this.counter);

    }

    updateCounter(total){
        this.counter.innerHTML = "Boops: " + total
    }

    boopAction(callback){
        let self = this;
        this.image.export.addEventListener( 'click', ()=>{

            self.animate()
            console.log("boop!")
            callback();
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
