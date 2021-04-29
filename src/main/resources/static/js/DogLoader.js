import BoopImageList from "./BoopImageList.js"
import MapBoxProfile from "./MapBoxProfile.js"
import HoverImage from "./HoverImage.js"

export default class DogLoader{

    constructor(id){
        this.notfy = new Notyf();
        this.dogName = document.getElementById("dogName");
        this.dogSex = document.getElementById("dogSex");
        this.currentDog = id;
        let self = this;
        axios.get('/api/dogs/'+id).then( res =>{

            document.getElementById("profilePicture").innerHTML = "";

            self.dogName.innerHTML = res.data.name;

            if(res.data.sex ){
                self.dogSex.innerHTML = `<i class="fas fa-mars"></i> Male`
            }else{
                self.dogSex.innerHTML = `<i class="fas fa-venus"></i> Female`
            }

            if(res.data.images[0].url != undefined){
                self.profilePicture = new HoverImage(res.data.images[0].url,"#").draw("profilePicture");
            }else{
                self.profilePicture = new HoverImage("http://localhost:8080/img/noDog.png","#").draw("profilePicture");
            }
            
            self.map = new MapBoxProfile(res.data, "map");
            self.boopImageList = new BoopImageList(res.data.id, "images");

        }).catch( err =>{
            self.notfy.error("Error loading dog!")
            setTimeout(function () {
                window.location.href = "/home"
            }, 1000);
        })
    }

    changeDog(id){
        let self = this;
        axios.get('/api/dogs/'+id).then( res =>{

            self.currentDog = res.data.id;


            self.dogName.innerHTML = res.data.name;

            if(res.data.sex ){
                self.dogSex.innerHTML = `<i class="fas fa-mars"></i> Male`
            }else{
                self.dogSex.innerHTML = `<i class="fas fa-venus"></i> Female`
            }

            //clear image
            document.getElementById("profilePicture").innerHTML = "";
            if(res.data.images[0].url != undefined){
                self.profilePicture = new HoverImage(res.data.images[0].url,"#").draw("profilePicture");
            }else{
                self.profilePicture = new HoverImage("http://localhost:8080/img/noDog.png","#").draw("profilePicture");
            }

            self.map.updateLocation(res.data)
            document.getElementById("images").innerHTML = "";
            self.boopImageList = new BoopImageList(res.data.id, "images");

        }).catch( err =>{
            self.notfy.error("Error loading dog!")
            setTimeout(function () {
                window.location.href = "/profile/" + self.currentDog;
            }, 1000);
        })
    }
}