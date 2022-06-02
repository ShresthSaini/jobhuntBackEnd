package com.jBProject.jobHuntProject.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id

class ContactUs(
    @Id
    @JsonIgnore
    var contactId :String?,

    var name : String?,
    var email :String,
    var subject: String?,
    var message : String?
)