export default class BreedEditor{

    constructor(tableGenerator, element){

        this.tableGenerator = tableGenerator;

        this.element = document.getElementById(element);
        this.notfy = new Notyf();

        let h5 = document.createElement("h5");
        h5.innerHTML = "Add Breed";
        h5.setAttribute("class","col col-md-3")

        this.input = document.createElement("input");
        this.input.type = "text";
        this.input.placeholder = "Breed Name";
        this.input.setAttribute("class","form-control col")

        

        this.button = document.createElement("button");
        this.button.setAttribute("class","btn btn-primary col-12 col-md-2 mx-2 mt-2 mt-md-0");
        this.button.innerHTML = "Add"
        this.button.addEventListener('click', ()=>{
            this.addBreed();
        })

        this.element.appendChild(h5);
        this.element.appendChild(this.input);
        this.element.appendChild(this.button);

        this.getBreeds();
    }

    addBreed(){
        let data = new FormData();
        data.append("name", this.input.value);
        let self = this;
        axios.post("/api/breed", data).then( res =>{
            if(self.debug){
                console.log(res)
            }
            self.toast(res.data)
            self.getBreeds();
        })  
    }

    getBreeds(){
        let self = this;

        axios.get("/api/breed").then( res =>{

            if(self.debug){
                console.log(res)
            }

            res.data.forEach( breed =>{
                
                self.tableGenerator.addRow([
                    breed.id,
                    breed.name,
                    self.createEditInput(breed.id),
                    self.createButtonGroup(breed.id, breed.name)
                ])
            })

            self.tableGenerator.drawTable()
            self.tableGenerator.clearRows();
        })
    }

    deleteBreed(id){
        let self = this;
        axios.delete("/api/breed/"+id).then( res =>{
            self.toast(res.data);
            self.getBreeds();
        }).catch( err =>{
            this.notfy.error("The breed is currently in use by dogs")
        })
    }

    createButtonGroup(id, name){
        let buttonGroup = document.createElement("td");
        buttonGroup.setAttribute("class","btn-group w-100");
        buttonGroup.setAttribute("role","group");

        let editButton = document.createElement("button");
        editButton.setAttribute("class","btn btn-primary");
        editButton.innerHTML = "Edit"
        
        let deleteButton = document.createElement("button");
        deleteButton.setAttribute("class","btn btn-danger");
        deleteButton.setAttribute("data-bs-toggle","modal");
        deleteButton.setAttribute("data-bs-target","#staticBackdrop");
        deleteButton.innerHTML = "Delete"
        

        editButton.addEventListener('click', ()=>{
            let name = document.getElementById("ed-" + id).value;
            let data = new FormData();
            let self = this;
            data.append("name", name);
            axios.put("/api/breed/" + id, data).then( res =>{
                self.toast(res.data)
                self.getBreeds();
            })
        })

        deleteButton.addEventListener('click', ()=>{
            //changes modal text
            let breedValue = document.getElementById("breedValue");
            breedValue.innerHTML = name;

            let deleteConfirmButton = document.getElementById("deleteConfirm");
            deleteConfirmButton.onclick = ()=>{ this.deleteBreed(id) };
            
        })

        

        buttonGroup.appendChild(editButton);
        buttonGroup.appendChild(deleteButton);

        return buttonGroup;

    }

    createEditInput(id){
        let container = document.createElement("td");
        container.setAttribute("class"," m-0");

        let input = document.createElement("input");
        input.placeholder = "Edit Breed Name"
        input.id = "ed-"+ id
        input.setAttribute("class"," form-control");

        container.appendChild(input);
        
        return container;
    }

    toast(data){

        if(data.message){
            this.notfy.success(data.message)
        }

        if(data.error){
            this.notfy.error(data.error)
        }

        if(data.warning){
            this.notfy.warning(data.warning)
        }

    }
}