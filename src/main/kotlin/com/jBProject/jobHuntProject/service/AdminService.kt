package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.model.Admin

import com.jBProject.jobHuntProject.model.Recruiter
import com.jBProject.jobHuntProject.model.AdminDTO


interface AdminService {
    fun addAdmin(adminDTO: AdminDTO):String
    fun getAdminById(email:String): Admin
    fun deleteAdmin(email:String):String
    fun updateAdmin(updateAdmin: AdminDTO): Admin
    fun getAllAdmins():List<Admin>
//    fun contactMsg(contactUs: ContactUs):String
//    fun getMesgReceived():List<ContactUs>?
    fun getAllRecruiters():List<Recruiter>

}