package com.nghycp.assgfreelanceservice

class ModelJob {

    var title:String = ""
    var category:String = ""
    var Description:String = ""
    var Address:String = ""
    var State:String = ""
    var Salary:String = ""
    var uid:String =""

    constructor()
    constructor(
        title: String,
        category: String,
        Description: String,
        Address: String,
        State: String,
        Salary: String,
        uid: String
    ) {
        this.title = title
        this.category = category
        this.Description = Description
        this.Address = Address
        this.State = State
        this.Salary = Salary
        this.uid = uid
    }


}