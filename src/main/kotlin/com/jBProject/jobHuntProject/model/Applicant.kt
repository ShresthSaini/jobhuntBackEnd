package com.jBProject.jobHuntProject.model


import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.util.*

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Document
data class Applicant @JvmOverloads constructor(

    var role:Role,
    var name:String,
    @Id
    @field:Email(message = "Enter a valid email address")
    var applicantEmail: String,
    @field:NotBlank(message = "it's mandatory")
    var password:String,
    @field:NotBlank(message = "it's mandatory")
    var type:String?="Applicant",



    @field:Pattern(regexp = "^\$|[0-9]{10}", message = "Enter your 10 digits mobile number")
    var phoneNumber: String,
    @field:NotBlank(message = "it's mandatory")
    var gender:String,

    @field:NotBlank(message = "it's mandatory")
    var education:String,
    @field:NotBlank(message = "it's mandatory")
    var instituteName:String,

    var startYear:Date,

    var endYear: Date,
    @field:NotBlank(message = "it's mandatory")
    var skills: MutableList<String>? = mutableListOf(),
    var jobProfile:String?,
    @field:NotBlank(message = "it's mandatory")
    var numOfExp: Number?,
    var appliedJobs: MutableList<GetJob>? = mutableListOf(),

    @JsonIgnore
    var isLoggedIn:Boolean? = false,

    )