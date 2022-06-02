package com.jBProject.jobHuntProject.DAO

import com.jBProject.jobHuntProject.model.Admin
import org.springframework.data.mongodb.repository.MongoRepository

import org.springframework.stereotype.Repository

@Repository
interface AdminDAO: MongoRepository<Admin,String>{
    fun existsByAdminEmail(email:String):Boolean
    fun existsByAdminEmailAndPassword(email:String,password:String):Boolean

}