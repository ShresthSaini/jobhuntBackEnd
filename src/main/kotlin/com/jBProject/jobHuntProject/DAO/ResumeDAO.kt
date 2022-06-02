package com.jBProject.jobHuntProject.DAO


import com.jBProject.jobHuntProject.model.Resume
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ResumeDAO : MongoRepository<Resume,String> {
    fun existsByApplicantEmail(applicantEmail:String):Boolean

}