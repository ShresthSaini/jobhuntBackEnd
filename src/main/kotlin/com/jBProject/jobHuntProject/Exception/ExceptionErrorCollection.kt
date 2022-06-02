package com.jBProject.jobHuntProject.Exception

import org.springframework.http.HttpStatus

data class ExceptionErrorCollection(
    var _message:String?,
    var status:HttpStatus= HttpStatus.BAD_REQUEST,
    var code:Int = status.value()
)