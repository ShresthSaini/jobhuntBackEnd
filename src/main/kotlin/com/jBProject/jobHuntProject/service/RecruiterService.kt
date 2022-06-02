package com.jBProject.jobHuntProject.service


import com.jBProject.jobHuntProject.model.*

interface RecruiterService {

    fun addRecruiter(recruiterDTO: RecruiterDTO):String
    fun addJob(jobDTO: JobDTO,recruiterEmail: String) : String
    fun acceptedApplicants(jobId: String):List<GetApplicant>
    fun getRecruiterById(recruiterEmail:String):Recruiter
    fun getAllApplicants(): List<GetApplicant>
    fun callForInterview(interviewCall: InterviewCall):String
    fun updateRecruiter(recruiterEmail: String,recruiter: Recruiter):String
    fun deleteRecruiter(recruiterEmail: String):String
    fun setJobStatus(recruiterEmail: String,jobId:String,status:String,getApplicant: GetApplicant):Any
    fun getJobPosted(recruiterEmail: String):List<Job>?
    fun getJobStatusById(jobTitle: String):JobStatus
    fun deleteJobById(recruiterEmail: String,jobId: String):String
    fun getAppliedApplicants(jobId: String):Any
    fun updateJob(jobId:String,job: Job):String


}