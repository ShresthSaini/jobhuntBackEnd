package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.model.*
import org.springframework.web.multipart.MultipartFile


interface ApplicantService {

    fun addApplicant(applicantDTO: ApplicantDTO): String
    fun addResume(resume:MultipartFile,applicantEmail: String):String
    fun getResume(applicantEmail: String):Resume
    fun applyForJob(applicantEmail: String, getJob: GetJob): String
    fun getJobById(jobId: String): Job
    fun getAllJobs(): List<GetJob>
    fun getApplicantById(applicantEmail: String): Applicant
    fun getJobStatus(statusId: String): Any
    fun updateApplicant(applicantEmail: String, applicant: Applicant): String
    fun deleteApplicant(applicantEmail: String): String
    fun deleteJobStatus(applicantEmail: String, jobTitle: String): String
    fun getAppliedJob(applicantEmail: String): List<GetJob>?
}