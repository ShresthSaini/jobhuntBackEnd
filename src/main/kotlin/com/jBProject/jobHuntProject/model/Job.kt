package com.jBProject.jobHuntProject.model


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

import javax.validation.constraints.NotBlank

@Document
data class Job(
    @Id
    var jobId:String,
    var jobTitle:String,

    var maxApplicants: Int?,

    var maxPositions: Int?,

    var activeApplications: Int?,

    @field:NotBlank(message = "it's mandatory")
    var dateOfPosting: String,

    @field:NotBlank(message = "it's mandatory")
    var lastDateToApply: LocalDate,

    var acceptedCandidates: MutableList<GetApplicant>? = mutableListOf(),

    var preferableSkills: MutableList<String>?= mutableListOf(),
    @field:NotBlank(message = "it's mandatory")
    var requiredExp: Int?=null,
    var appliedApplicants:MutableList<GetApplicant>?= mutableListOf(),
    var recruiterEmail: String,
    var companyName: String?,
    var companyAddress: String?,

    )