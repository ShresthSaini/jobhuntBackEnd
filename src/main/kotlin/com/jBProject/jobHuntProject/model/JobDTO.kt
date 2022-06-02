package com.jBProject.jobHuntProject.model



import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import javax.validation.constraints.NotBlank

@Document

class JobDTO(
    @Id
    var jobId :String =ObjectId().toString(),
    var jobTitle: String,

    var maxApplicants: Int?,

    var maxPositions: Int?,

    var dateOfPosting: String= LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
    var lastDateToApply: LocalDate ,
    var preferableSkills: MutableList<String>? = mutableListOf(),
    var requiredExp: Int? = null
)