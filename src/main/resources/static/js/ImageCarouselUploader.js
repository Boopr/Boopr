/**
     * ImageCarouselUploader
     * @author Jacob Gonzalez
     * @constructor 
     * @param {carousel} The element that the carousel will be injected into
     * @param {userID} the id of the current dog 
     */
 export default class ImageCarouselUploader{

    element;
    carousel;
    carouselInner;
    file;
    button;
    id;

    constructor(carousel, dogID){

        //create the carousel in the id given above
        this.element = document.getElementById(carousel);
        this.id = dogID;

        this.carousel = document.createElement("div");
        this.carousel.setAttribute("id","carouselImages")
        this.carousel.setAttribute("class","carousel slide");
        this.carousel.setAttribute("data-bs-ride","carousel");

        this.carouselInner = document.createElement("div");
        this.carouselInner.setAttribute("class","carousel-inner");
        this.carousel.appendChild(this.carouselInner);

        

        let backward = document.createElement("button");
        backward.setAttribute("class","carousel-control-prev");
        backward.setAttribute("type","button");
        backward.setAttribute("data-bs-target","#carouselImages")
        backward.setAttribute("data-bs-slide","prev");
        backward.innerHTML = `<span class="carousel-control-prev-icon" aria-hidden="true"></span>
                              <span class="visually-hidden">Previous</span>`


        let forward = document.createElement("button");
        forward.setAttribute("class","carousel-control-next");
        forward.setAttribute("type","button");
        forward.setAttribute("data-bs-target","#carouselImages")
        forward.setAttribute("data-bs-slide","next");
        forward.innerHTML = `<span class="carousel-control-next-icon" aria-hidden="true"></span>
                             <span class="visually-hidden">Next</span>`

        this.carousel.appendChild(backward);
        this.carousel.appendChild(forward);

        this.file = document.createElement("input");
        this.file.setAttribute("type","file");
        this.button = document.createElement("button");
        this.button.setAttribute("class","btn btn-primary")
        this.button.innerHTML = "Upload Image"

        this.element.appendChild(this.carousel);
        this.element.appendChild(this.file);
        this.element.appendChild(this.button);

        this.button.addEventListener('click', ()=>{this.uploadImage()})
        this.refreshDogs()
    }

    uploadImage(){
        let data = new FormData();
        data.set("file", this.file.files[0]);
        let self = this;
        axios.post(`/api/dogs/${this.id}/pics/`, data).then(res =>{
            Toastify({
                text: res.data.message,
                backgroundColor: "green",
                gravity: "bottom",
                className: "info",
            }).showToast();
            self.refreshDogs();
        })
    }

    refreshDogs(){
        let self = this;
        axios.get(`/api/dogs/${this.id}/pics/`).then( res =>{
            self.clearImages();
            res.data.forEach( (picture, i) =>{
                self.newImage(picture, i)
            })
        })
    }
    
    clearImages(){
        this.carouselInner.innerHTML = "";
    }

    newImage(picture , i ){
        let item = document.createElement("div");
        if(i == 0){
            item.setAttribute("class","carousel-item active");
        }else{
            item.setAttribute("class","carousel-item");
        }
        

        let imageContainer = document.createElement("div")
        imageContainer.setAttribute("class","justify-content-center");
        imageContainer.style.backgroundImage = `url(${picture.url})`;
        imageContainer.style.backgroundSize = "cover";
        imageContainer.style.filter = "blur(2px)";
        imageContainer.style.height = "200px";

        let image = document.createElement("img");
        image.setAttribute("class","d-block position-relative");
        image.style.height = "200px";
        image.style.margin = "0 auto -200px auto"
        image.style.top = "-200px"
        image.src = picture.url;
        

        let caption = document.createElement("div")
        caption.setAttribute("class","carousel-caption d-flex justify-content-between align-items-end")
        
        let boops = document.createElement("div");
        if(picture.boops.length == undefined){
            boops.innerHTML = "Total boops: 0"
        }else{
            boops.innerHTML = "Total boops: " + picture.boops.length
        }

        let button = document.createElement("button");
        button.setAttribute("class","btn btn-danger")
        button.innerHTML = "Delete";
        button.addEventListener('click', ()=>{ this.delete(picture.id)})

        caption.appendChild(boops);
        caption.appendChild(button);


        item.appendChild(imageContainer);
        item.appendChild(image);
        item.appendChild(caption);
        this.carouselInner.appendChild(item);
    }

    delete(pid){
        let self = this;
        axios.post(`/api/dogs/pics/${pid}/delete`).then(res =>{
            console.log(res)
            Toastify({
                text: res.data.message,
                backgroundColor: "green",
                gravity: "bottom",
                className: "info",
            }).showToast();
            self.refreshDogs();
        })
    }


}
