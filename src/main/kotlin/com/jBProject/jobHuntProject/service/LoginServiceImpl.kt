package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.DAO.AdminDAO
import com.jBProject.jobHuntProject.DAO.ApplicantDAO
import com.jBProject.jobHuntProject.DAO.RecruiterDAO
import com.jBProject.jobHuntProject.Exception.GetException
import com.jBProject.jobHuntProject.model.*

import com.jBProject.jobHuntProject.util.JwtUtils
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class LoginServiceImpl(
    private var applicantDAO: ApplicantDAO,
    private var recruiterDAO: RecruiterDAO,
    private var adminDAO: AdminDAO,
    private var recruiterService: RecruiterService,
    private var applicantService: ApplicantService,
    private var adminService: AdminService,
    private var sendEmailServiceImpl: SendEmailImpl,
    var utils: JwtUtils
):LoginService {

    override fun login(loginDTO: LoginDTO, response: HttpServletResponse):Any {

        return if(recruiterDAO.existsByRecruiterEmailAndPassword(loginDTO.email,loginDTO.password)){

            val recruiter = this.recruiterService.getRecruiterById(loginDTO.email)

            recruiter.isLoggedIn = true
            recruiterDAO.save(recruiter)

            val jwt = utils.generateJwt(recruiter.recruiterEmail,recruiter.type!!, response)


            mutableListOf(recruiter.type,recruiter.isLoggedIn,jwt)



        } else if(applicantDAO.existsByApplicantEmailAndPassword(loginDTO.email,loginDTO.password)) {
            val applicant:Applicant = this.applicantService.getApplicantById(loginDTO.email)
            applicant.isLoggedIn = true
            applicantDAO.save(applicant)
            val jwt= utils.generateJwt(applicant.applicantEmail,applicant.type!!,response)
            mutableListOf(applicant.type,applicant.isLoggedIn,jwt)

        }else if(adminDAO.existsByAdminEmailAndPassword(loginDTO.email, loginDTO.password)){
            val admin: Admin = this.adminService.getAdminById(loginDTO.email)
            admin.isLoggedIn=true
            adminDAO.save(admin)
            val jwt = utils.generateJwt(admin.adminEmail, admin.type, response)

            mutableListOf(admin.type,admin.isLoggedIn,jwt)

        }
        else{
            throw GetException ("login failed,please check your email and password")
        }
    }

    override fun forgetPassword(email: String): String {
        if(this.adminDAO.existsByAdminEmail(email)){
            val admin = this.adminDAO.findById(email).get()
            val subject = "Welcome to Job Hunt"
            val body =  "Hi Admin,\n Your request for forget password has been received   \nYour Login details are: \nEmail: ${admin.adminEmail}\nPassword: ${admin.password}.\n\n regards,\n Job Hunt Team"
            sendEmailServiceImpl.sendMail(admin.adminEmail, body, subject)
            return "Password successfully sent to your registered email."
        }
        else if(this.recruiterDAO.existsByRecruiterEmail(email)){
            val recruiter = this.recruiterDAO.findById(email).get()
            val subject = "Welcome to Job Hunt"
            val body =  "Hi ${recruiter.name} ,\n Your request for forget password has been received   \nYour Login details are: \nEmail: ${recruiter.recruiterEmail}\nPassword: ${recruiter.password}.\n\n regards,\n Job Hunt Team"
            sendEmailServiceImpl.sendMail(recruiter.recruiterEmail, body, subject)
            return "Password successfully sent to your registered email."
        }
        else if(this.applicantDAO.existsByApplicantEmail(email)){
            val applicant = this.applicantDAO.findById(email).get()
            val subject = "Welcome to Job Hunt"
            val body =  "Hi ${applicant.name} ,\n Your request for forget password has been received   \nYour Login details are: \nEmail: ${applicant.applicantEmail}\nPassword: ${applicant.password}.\n\n regards,\n Job Hunt Team"
            sendEmailServiceImpl.sendMail(applicant.applicantEmail, body, subject)
            return "Password successfully sent to your registered email."
        }
        else {
            throw GetException("Email isn't registered with us")
        }


    }

    override fun logout(logoutDTO: LogoutDTO): Any {
        val logout = "Logout Successful"

        return if(recruiterDAO.existsByRecruiterEmail(logoutDTO.email)){
            val recruiter: Recruiter = this.recruiterService.getRecruiterById(logoutDTO.email)
            recruiter.isLoggedIn = false
            recruiterDAO.save(recruiter)
            logout
        } else if(applicantDAO.existsByApplicantEmail(logoutDTO.email)) {
            val applicant: Applicant = this.applicantService.getApplicantById(logoutDTO.email)
            applicant.isLoggedIn = false
            applicantDAO.save(applicant)
            logout
        }else if(adminDAO.existsByAdminEmail(logoutDTO.email)) {
            val admin: Admin = this.adminService.getAdminById(logoutDTO.email)
            admin.isLoggedIn = true
            adminDAO.save(admin)
            logout
        }
        else{
            "logout failed,wait and try again"
        }
    }

}