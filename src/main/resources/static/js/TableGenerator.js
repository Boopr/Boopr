export default class TableGenerator{

    constructor(element, headRow){
        this.element = document.getElementById(element);

        this.table = document.createElement("table");
        this.table.setAttribute("class","table table-striped");
        //table head
        this.thead = document.createElement("thead");
        this.headRow = this.createRow();
        this.headRows = [];

        headRow.forEach( data =>{
            this.headRows.push(this.createTableHead(data))
        })

        this.headRows.forEach( row =>{
            this.headRow.appendChild(row);
        })

        this.tableBody = document.createElement("tbody");
        this.tableRows = [];

        this.thead.appendChild(this.headRow);            
        this.table.appendChild(this.thead);
        this.table.appendChild(this.tableBody);
        this.element.appendChild(this.table)

    }

    clearRows(){
        this.tableRows = [];
    }

    createRow(){
        let row = document.createElement("tr")
        return row;
    }

    createTableHead(text){
        let th = document.createElement("th");
        th.innerHTML = text;
        th.setAttribute("scope","col");
        return th;
    }

    /**
     * @parm {String} Data accepts arrays 
     * 
     */
    addRow(data){
        let row = this.createRow();
        data.forEach( (data, i)=>{
            //check if the data is an element
            try{
                if(data.tagName != undefined){
                    row.appendChild(data);
                    return;
                }
            }catch(e){
                console.log(e)
            }
            
            //if not just take the string or number and put it in the row
            let td = this.createData(data)
            if(i == 0){
                td.setAttribute("scope","row");
            }
            row.appendChild(td);
        })
        this.tableRows.push(row);
    }

    createData(text){
        let td = document.createElement("td")
        td.innerHTML = text
        return td;
    }

    drawTable(){
        //clear the body
        this.tableBody.innerHTML = "";
        this.tableRows.forEach( tableRow =>{
            this.tableBody.appendChild(tableRow);
        })
    }
}