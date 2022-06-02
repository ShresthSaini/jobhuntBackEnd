package com.jBProject.jobHuntProject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document



@Document
class Resume(
   @Id
   var applicantEmail: String,
   var resume:String
)