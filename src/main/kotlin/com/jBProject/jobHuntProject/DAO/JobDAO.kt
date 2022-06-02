package com.jBProject.jobHuntProject.DAO

import com.jBProject.jobHuntProject.model.Job
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface JobDAO : MongoRepository<Job,String> {
    fun existsByJobTitle(jobTitle:String):Boolean




}