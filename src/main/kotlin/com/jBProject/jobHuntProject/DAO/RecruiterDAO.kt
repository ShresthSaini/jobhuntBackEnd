package com.jBProject.jobHuntProject.DAO

import com.jBProject.jobHuntProject.model.Recruiter
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RecruiterDAO : MongoRepository<Recruiter, String> {
    fun existsByRecruiterEmail(recruiterEmail:String):Boolean
    fun existsByRecruiterEmailAndPassword(recruiterEmail: String,password:String):Boolean
}