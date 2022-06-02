package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.model.LoginDTO
import com.jBProject.jobHuntProject.model.LogoutDTO
import javax.servlet.http.HttpServletResponse


interface LoginService {
    fun login(loginDTO: LoginDTO,response: HttpServletResponse):Any
    fun forgetPassword(email:String):String
    fun logout(logoutDTO: LogoutDTO):Any
}