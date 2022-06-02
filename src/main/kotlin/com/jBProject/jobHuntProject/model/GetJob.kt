package com.jBProject.jobHuntProject.model

import java.time.LocalDate
import java.util.*


class GetJob(
    var jobId:String,
    var jobTitle: String,
    var dateOfPosting: String,
    var lastDateToApply: LocalDate,
    var preferableSkills: MutableList<String>? = mutableListOf(),
    var requiredExp: Int?,
    var recruiterEmail: String,
    var companyName: String?,
    var companyAddress: String?,
    var dateOfApply: LocalDate? = LocalDate.now(),
    var statusId:String?

)