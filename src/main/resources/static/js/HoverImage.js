export default class HoverImage{

    constructor(url, anchor, width = "300px", height){
        this.container = document.createElement("div");
        this.container.style.overflow = "hidden"
        this.anchor = document.createElement("a");
        this.anchor.style.overflow = "hidden"
        this.anchor.style.margin = "0px"

        this.element = document.createElement("div");
        //setup anchor tag
        this.anchor.href = anchor;
        this.anchor.appendChild(this.element);
        this.container.appendChild(this.anchor)

        this.imageContainer = document.createElement("a");
        this.imageContainer.href = anchor;
        this.imageContainer.style.overflow = "hidden"
        this.imageContainer.style.margin = "0px"

        this.width;
        this.height;
        let self = this;

        this.export = document.createElement("div");
        this.export.appendChild(this.container);
        this.export.appendChild(this.imageContainer);


        this.img = new Image();
        this.img.onload = function(){
            self.width = this.width;
            self.height = this.height;
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
            self.img.style.top = `-${width}`
            self.img.style.width = width

            this.style.marginBottom = `-${width}`

            self.export.style.height = height || width;
            self.export.style.width =  width;
            self.imageContainer.appendChild(this)
        };

        
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