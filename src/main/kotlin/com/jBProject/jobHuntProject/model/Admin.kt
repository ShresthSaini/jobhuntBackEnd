package com.jBProject.jobHuntProject.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import javax.validation.constraints.Email


data class Admin(
    val role: Role,
    var name:String,
    @Id
    @field:Email(message = "Email should be valid")
    var adminEmail: String,
    var password:String,
    var type:String="Admin",
    var adminAddress:String,
    @JsonIgnore
    var isLoggedIn:Boolean?=false,

)