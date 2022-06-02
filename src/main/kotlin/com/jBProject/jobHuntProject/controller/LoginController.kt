package com.jBProject.jobHuntProject.controller

import com.jBProject.jobHuntProject.Exception.GetException
import com.jBProject.jobHuntProject.model.*
import com.jBProject.jobHuntProject.service.AdminService
import com.jBProject.jobHuntProject.service.ApplicantService
import com.jBProject.jobHuntProject.service.LoginService
import com.jBProject.jobHuntProject.service.RecruiterService
import com.jBProject.jobHuntProject.util.JwtUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin(origins = ["http://localhost:3000/"], allowCredentials = "true")
class LoginController(
    private var loginService: LoginService,
    var util: JwtUtils,
    private  var applicantService: ApplicantService,
    private var recruiterService: RecruiterService,
    private var adminService: AdminService
) {

    private val jwtNull = "Email and password Not Found"
    private val invalidType = "Invalid User"


    @PutMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO, response: HttpServletResponse): Any {
        return this.loginService.login(loginDTO, response)
    }



    @GetMapping("/responseApplicant")
    fun applicant (@CookieValue("Applicant") jwt: String?): Applicant {
        try {
            if (jwt == null) {
                throw GetException(jwtNull)
            }
            val body = util.verify(jwt)

            if (body["type"]?.equals("Applicant") == true) {
                return this.applicantService.getApplicantById(body.issuer)
            } else {
                throw GetException(invalidType)
            }
        } catch (e: Exception) {
            throw GetException("unauthenticated")
        }
    }

 @GetMapping("/responseRecruiter")
    fun recruiter (@CookieValue("Recruiter") jwt: String?): Recruiter {
        try {
            if (jwt == null) {
                throw GetException(jwtNull)
            }
            val body = util.verify(jwt)

            if (body["type"]?.equals("Recruiter") == true) {
                return this.recruiterService.getRecruiterById(body.issuer)
            } else {
                throw GetException(invalidType)
            }
        } catch (e: Exception) {
            throw GetException("unauthenticated")
        }
    }

    @GetMapping("/responseAdmin")
     fun admin(@CookieValue("Admin") jwt:String?):Admin {
        try {
            if (jwt == null) {
                throw GetException(jwtNull)
            }
            val body = util.verify(jwt)

            if (body["type"]?.equals("Admin") == true) {
                return this.adminService.getAdminById(body.issuer)
            } else {
                throw GetException(invalidType)
            }
        } catch (e: Exception) {
            throw GetException("unauthenticated")
        }

     }

    @PutMapping("/logout")
    fun logout(@RequestBody logoutDTO: LogoutDTO,response:HttpServletResponse): Any {

        val cookie = Cookie(logoutDTO.type, "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return this.loginService.logout(logoutDTO)
    }

    @GetMapping("/forgetPassword/{email}")
    fun forgetPassword(@PathVariable email: String): String {
        return this.loginService.forgetPassword(email)
    }
}