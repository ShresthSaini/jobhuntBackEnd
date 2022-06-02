package com.jBProject.jobHuntProject.DAO

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import com.jBProject.jobHuntProject.model.JobStatus

@Repository
interface JobStatusDAO : MongoRepository<JobStatus,String> {
    fun existsByApplicantEmailAndJobTitle(applicantEmail:String,jobTitle:String):Boolean
    fun existsByJobTitle(jobTitle: String):Boolean

}