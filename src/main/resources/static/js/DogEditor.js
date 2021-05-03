export default class DogEditor{

    constructor(tableGenerator){

        this.tableGenerator = tableGenerator;

        this.notfy = new Notyf();

        this.input = document.createElement("input");
        this.input.type = "text";
        this.input.placeholder = "Breed Name";
        this.input.setAttribute("class","form-control col")

        this.getDogs();
    }

    getDogs(){
        let self = this;

        axios.get("/api/dogs").then( res =>{

            if(self.debug){
                console.log(res)
            }

            res.data.forEach( dog =>{
                self.tableGenerator.addRow([
                    dog.id,
                    dog.name,
                    dog.allBoops,
                    dog.owner.username,
                    self.createButtonGroup(dog.id, dog.name)
                ])
            })

            self.tableGenerator.drawTable()
            self.tableGenerator.clearRows();
        })
    }

    deleteDog(id){
        let self = this;
        axios.delete("/api/dog/remove/"+id).then( res =>{
            self.toast(res.data);
            self.getDogs();
        }).catch( err =>{
            this.notfy.error("Error deleting the dog")
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
            window.location.href = "/dog/edit/" + id
        })

        deleteButton.addEventListener('click', ()=>{
            //changes modal text
            let breedValue = document.getElementById("breedValue");
            breedValue.innerHTML = name;

            let deleteConfirmButton = document.getElementById("deleteConfirm");
            deleteConfirmButton.onclick = ()=>{ this.deleteDog(id) };
            
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