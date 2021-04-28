import BoopImage from "./BoopImage.js"

export default class BoopImageList{

    constructor(dogId, element){
        this.id = dogId;
        this.element = document.getElementById(element);
        this.notfy = new Notyf();
        this.images = [];
        this.getImages()
        return this;
    }

    getImages(){
        let self = this;
        axios.get("/api/dogs/" + this.id).then(res=>{
            res.data.images.forEach( image =>{
                let img = new BoopImage(image);

                img.updateCounter(image.boops)
                //define the action when clicking the image
                img.boopAction( ()=>{
                    axios.post("/api/pics/" + img.id + "/boop").then( res =>{
                        img.updateCounter(res.data.totalBoops)
                        self.toast(res.data)
                    }).catch(err=>{
                        self.notfy.error(err)
                    })
                })

                self.images.push(img);
            })
            self.drawImages();
        }).catch(err=>{
            self.notfy.error(err)
        })
    }

    drawImages(){
        this.element.innerHTML = "";
        let self = this;
        this.images.forEach( image =>{
            let img = image.export()
            this.element.appendChild(img)
        })
    }

    toast(data){

        if(data.message){
            this.notfy.success(data.message)
            if(this.redirect){
                setTimeout(function () {
                    window.location.href = this.redirect;
                }, 1000);
            }
        }

        if(data.error){
            this.notfy.error(data.error)
        }

        if(data.warning){
            this.notfy.warning(data.warning)
        }

    }

}
