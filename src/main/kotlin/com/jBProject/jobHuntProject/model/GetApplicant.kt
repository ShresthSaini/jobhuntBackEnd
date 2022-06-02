package com.jBProject.jobHuntProject.model



import java.util.*


class GetApplicant(
    var name:String,

    var applicantEmail: String,

    var phoneNumber: String,
    var gender:String,

    var education :String,
    var instituteName :String,

    var startYear : Date,

    var endYear : Date,
    var skills: MutableList<String>? = mutableListOf(),
    var jobProfile:String?,
    var numOfExp: Number?,
    var statusId:String?

    )