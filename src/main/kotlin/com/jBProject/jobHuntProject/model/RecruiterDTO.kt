package com.jBProject.jobHuntProject.model


import org.springframework.data.annotation.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern



class RecruiterDTO (
    @field:NotBlank(message = "it's mandatory")
    var name:String,
    @Id
    @field:Email(message = "Enter a valid email address")
    var recruiterEmail: String,
    @field:NotBlank(message = "it's mandatory")
    var password:String,
    @field:NotBlank(message = "it's mandatory")
    var type:String,


    @field:Pattern (regexp="^\$|[0-9]{10}", message = "Enter your 10 digits mobile number")
    var phoneNumber: String,
    @field:NotBlank(message = "it's mandatory")
    var gender:String,
    @field:NotBlank(message = "it's mandatory")
    var companyName :String,
    @field:Pattern (regexp="^\$|[0-9]{10}", message = "Enter your 10 digits mobile number")
    var companyContactNumber :String,
    @field:NotBlank(message = "it's mandatory")
    var companyAddress : String

)

