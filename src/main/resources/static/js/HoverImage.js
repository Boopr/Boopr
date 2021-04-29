export default class HoverImage{

    /**
     * 
     * @param {*} url link to the image displayed
     * @param {*} anchor Where the image when clicked will link to
     * @param {*} width (Optional) Width of the image
     * @param {*} height (Optional) Height of the image
     * @returns 
     */
    constructor(url, anchor, width = "300px", height){
        this.container = document.createElement("div");
        this.container.style.overflow = "hidden"
        this.anchor = document.createElement("a");
        this.anchor.style.overflow = "hidden"
        this.anchor.style.margin = "0px"

        this.element = document.createElement("div");
        //setup anchor tag
        if(anchor == undefined || anchor == null){

        }else{
            this.anchor.href = anchor;
        }
        
        this.anchor.appendChild(this.element);
        this.container.appendChild(this.anchor)

        this.imageContainer = document.createElement("div");

        if(anchor == undefined || anchor == null){

        }else{
            this.imageContainer.href = anchor;
        }
        
        this.imageContainer.style.overflow = "hidden"
        this.imageContainer.style.width = "300px"
        this.imageContainer.style.height = "300px"
        this.imageContainer.style.position = "relative"
        this.imageContainer.style.top = "-300px"

        this.width;
        this.height;
        let self = this;

        this.export = document.createElement("div");
        this.export.appendChild(this.container);
        this.export.appendChild(this.imageContainer);


        this.img = new Image();
        this.img.style.cursor = "pointer"
        this.img.onload = function(){
            self.width = this.width;
            self.height = this.height;
            //this.width = "100%"

            console.log(this.style.height)
            
            //execute element changes

            self.container.height = height || width;
            self.container.style.width = width;
        
            self.element.style.backgroundImage = `url(${url})`;
            self.element.style.backgroundSize = "200%";
            self.element.style.backgroundPosition = "50%";
            self.element.style.filter = "blur(5px)"

            self.element.style.width = width;
            self.element.style.height = height || width;

            let halfHeight = width.slice(0,-2)*2;
            //self.img.style.top = `-${width}`
            let m = this.height / this.width
            console.log(m)
            if(m > 1){
                this.style.height = "300px"
                this.style.left = `5px`
            }else{
                this.style.width = "100%"
                //strange ratio 
                this.style.top = `${m*78}px`
            }

            //this.style.marginBottom = `-${width}`

            self.export.style.height = height || width;
            self.export.style.width =  width;
            self.imageContainer.appendChild(this)
        };

        this.img.addEventListener('click', ()=>{
            if(anchor){
                window.location.href = anchor;
            }
            
        })
        
        this.img.src = url;
        this.img.style.position = "relative" 

        return this;
    }

    export(){
        return this.export;
    }

    draw(element){
        document.getElementById(element).appendChild(this.export);
    }

}