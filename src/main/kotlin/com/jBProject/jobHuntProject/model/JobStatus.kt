package com.jBProject.jobHuntProject.model

import org.springframework.data.annotation.Id


class JobStatus(
    @Id
    var statusId: String,
    var recruiterName:String?,
    var recruiterEmail: String,
    var applicantEmail: String,
    var jobId:String,
    var jobTitle:String?,
    var companyName:String?,
    var status : String?=null

)