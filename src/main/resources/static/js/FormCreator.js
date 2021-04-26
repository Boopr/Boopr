export default class formCreator {

    /**
     * The form creator relies on Axios and Notyf being imported before importing this module
     * 
     * @param {String} element The ID element or div the form will generate into
     * @param {String} url The url you will be sending the form data to
     * @param {String} method (Optional) POST, PUT, DELETE, GET; Defaults to POST
     * @param {String} redirect (Optional) The url that you will directed to upon success
     */
    constructor(element = "form", url, method = "post", redirect){

        this.element = document.getElementById(element);
        this.notfy = new Notyf();
        this.method = method;
        this.redirect = redirect;
        this.url = url;
        this.form = document.createElement("div");
        this.form.setAttribute("class","row p-2 col-12 col-lg-6 col-md-8 mx-auto mt-4")

        this.button = document.createElement("button");
        this.button.setAttribute("class","btn btn-primary order-last mt-2")
        this.button.innerHTML = "Submit"
        this.button.addEventListener('click', ()=>{
            this.submit();
        })

        this.form.appendChild(this.button);
        this.element.appendChild(this.form);
        
    }

    importForm(element){
        this.form.appendChild(element);
    }

    /**
     * 
     * @param {String} formName Defines the ID and the param sent in the formdata
     * @param {String} placeholder (Optional) If its a text field it will set the placeholder text
     * @param {String} type (Optional) define the type of input
     */
    createInput(formName, placeholder = "", type = "text" , value = ""){

        if(formName == undefined){
            console.error("You must specify a form name!");
        }

        let form = document.createElement("input");
        form.setAttribute("class","form-control mb-2");
        form.type = type;
        form.id = formName;
        form.name = formName;
        form.value = value;
        form.placeholder = placeholder;

        this.form.appendChild(form);

    }



    /**
     * 
     * @param {String} item Specify the id the label is for
     * @param {String} text The label text displayed to the user
     */
    createLabel(item, text){
        if(item == undefined){
            console.error("You must specify what this label is for!");
        }

        if(text == undefined){
            console.error("You must specify a label!");
        }

        let label = document.createElement("label");
        label.setAttribute("class","fw-bold mb-2 ps-0");
        label.for = item;
        label.innerHTML = text;
        this.form.appendChild(label);
    }

    /**
     * 
     * @param {Object} options --
     * @id  the id and formdata param, 
     * @multiselect  true or false to allow mutliselect on the select form
     * @options  An array with objects that define the value and text of the options in the select form 
     */
    createSelect(options){

        //set default values for fallback test
        let values = options || {
            id: "EmptySelect",
            multiselect: "true",
            options:[
                {
                value: "1",
                text: "Please input"
                },
                {
                value: "2",
                text: "an options object"
                }
            ]
        }

        let select = document.createElement("select");
        select.id = values.id;
        select.setAttribute("class","form-control mb-2")
        if(values.multiselect == "true"){
            select.setAttribute("multiple","");
        }

        values.options.forEach( option =>{
            let element = document.createElement("option");
            element.value = option.value;
            element.innerHTML = option.text;

            select.appendChild(element);
        })
        
        this.form.appendChild(select);
    }

    submit(){
        let data = new FormData();
        Array.from(this.form.children).forEach( element => {
            //do not include labels in the form data
            if(element.id.toLowerCase() == "mapbox-value"){
                let cords = element.value.split(",");
                data.append("longitude",cords[0].toString())
                data.append("latitude",cords[1].toString())
            }
            if(element.tagName == "DIV"){
                return;
            }
            if(element.type == "file"){
                data.append( element.id, element.files[0]);
            }
            if(element.tagName != "LABEL"){
                data.append( element.id, element.value);
            }
            

        })

        let self = this;
        switch(this.method.toLowerCase()){

            case "post":
                axios.post(this.url, data).then( res => {
                    if(self.debug){
                        console.log(res)
                    }
                    self.toast(res.data)
                })
                break;
            case "put":
                axios.put(this.url, data).then( res => {
                    if(self.debug){
                        console.log(res)
                    }
                    self.toast(res.data)
                })
                break;
            case "get":
                axios.get(this.url, data).then( res => {
                    if(self.debug){
                        console.log(res)
                    }
                    self.toast(res.data)
                })
                break;

        } 
        
    }

    debug(){
        this.debug = true;
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