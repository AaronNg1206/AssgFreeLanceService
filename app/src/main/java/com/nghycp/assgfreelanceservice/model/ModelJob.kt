package com.nghycp.assgfreelanceservice.model

class ModelJob {

    var id:String = ""
    var title:String = ""
    var category:String = ""
    var Description:String = ""
    var Address:String = ""
    var State:String = ""
    var Salary:String = ""
    var uid:String = ""
    var progressStatus:String = ""
    var idOnClick:String =""

    //empty constructor, required by firebase
    constructor()

    //parameter constructor
    constructor(
        id: String,
        title: String,
        category: String,
        Description: String,
        Address: String,
        State: String,
        Salary: String,
        uid: String,
        progressStatus: String,
        idOnClick:String
    ) {
        this.id = id
        this.title = title
        this.category = category
        this.Description = Description
        this.Address = Address
        this.State = State
        this.Salary = Salary
        this.uid = uid
        this.progressStatus = progressStatus
        this.idOnClick = idOnClick
    }

}