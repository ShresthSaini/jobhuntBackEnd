package com.jBProject.jobHuntProject.DAO


import com.jBProject.jobHuntProject.model.Applicant
import com.jBProject.jobHuntProject.model.GetJob
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ApplicantDAO: MongoRepository<Applicant, String> {
    fun existsByApplicantEmail(applicantEmail:String):Boolean
    fun existsBySkills(skills:String):Boolean
    fun existsByApplicantEmailAndPassword(applicantEmail: String,password:String):Boolean
    fun existsByAppliedJobs(getJob:GetJob):Boolean
    fun findByApplicantEmailContaining(appliedJobs: MutableList<GetJob>?)


}