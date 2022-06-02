package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.DAO.AdminDAO
import com.jBProject.jobHuntProject.DAO.RecruiterDAO
import com.jBProject.jobHuntProject.Exception.*
import com.jBProject.jobHuntProject.model.*
import org.springframework.stereotype.Service


@Service
class AdminServiceImpl(private var adminDAO: AdminDAO, private var recruiterDAO: RecruiterDAO):AdminService {


    override fun addAdmin(adminDTO: AdminDTO): String {
        if(this.adminDAO.existsByAdminEmail(adminDTO.adminEmail))
            throw AddException("Admin Does Exist for this email, please use another ")
        else{
            val adminTemp = Admin(Role.ADMIN,adminDTO.name,adminDTO.adminEmail,adminDTO.password,"Admin",adminDTO.adminAddress,false)
            this.adminDAO.insert(adminTemp)
            return "Admin Registered Successfully"
        }
    }

    override fun getAdminById(email: String):Admin {
        if(this.adminDAO.existsByAdminEmail(email))
            return this.adminDAO.findById(email).get()
        else
            throw GetException("Admin Doesn't Exist")
    }

    override fun deleteAdmin(email: String): String {
        if(this.adminDAO.existsByAdminEmail(email)) {
            this.adminDAO.deleteById(email)
            return "Deleted Successfully"
        }
        else
            throw DeleteException("Admin Doesn't Exist ")
    }

    override fun updateAdmin(updateAdmin: AdminDTO): Admin {
        if(adminDAO.existsByAdminEmail(updateAdmin.adminEmail)) {
            val admin=this.getAdminById(updateAdmin.adminEmail)
            admin.password = updateAdmin.password
            admin.name = updateAdmin.name
            admin.adminAddress = updateAdmin.adminAddress
            adminDAO.save(admin)
            return admin
        }
        else{
            throw GetException("Admin Doesn't Exist")
        }
    }

    override fun getAllAdmins(): List<Admin> {
      if(adminDAO.count()>0){
          return this.adminDAO.findAll()
      }else{
          throw GetAllException("No admins added yet")
      }
    }
//
    override fun getAllRecruiters(): List<Recruiter> {
       if(recruiterDAO.count()>0){
           return this.recruiterDAO.findAll()
       }else{
          throw GetAllException("No Recruiters has registered yet")
       }
    }
//    override fun contactMsg(contactUs: ContactUs): String {
//        if(contactUs.email.isEmpty()){
//            throw NullException("Email is Mandatory")
//        }
//        else{
//            val admin:Admin = this.getAdminById("admin.ben1@gmail.com")
//            admin.contactUsMsg?.add(contactUs)
//            adminDAO.save(admin)
//            return "Message Sent Successfully,Our team will get back to you shortly"
//        }
//    }

//    override fun getMesgReceived(): List<ContactUs>? {
//        return if(adminDAO.existsByEmail("admin.ben1@gmail.com")){
//            val adminTemp: Admin = this.adminDAO.findById("admin.ben1@gmail.com").get()
//            adminTemp.contactUsMsg?.toList()
//        }
//        else{
//            throw GetException("there is no mesg yet")
//        }
//    }

}