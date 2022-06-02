package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.DAO.*

import com.jBProject.jobHuntProject.Exception.AddException
import com.jBProject.jobHuntProject.Exception.DeleteException
import com.jBProject.jobHuntProject.Exception.GetAllException
import com.jBProject.jobHuntProject.Exception.GetException
import com.jBProject.jobHuntProject.model.*
import org.bson.types.ObjectId

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.*


@Service
class ApplicantServiceImpl(
    private var applicantDAO: ApplicantDAO, private var jobDAO: JobDAO, private var jobStatusDAO: JobStatusDAO,


    private var resumeDAO: ResumeDAO

) : ApplicantService {

    override fun getJobById(jobId: String): Job {
        if (jobDAO.existsById(jobId)) {
            return this.jobDAO.findById(jobId).get()
        } else {
            throw GetException("This Job doesn't exist")
        }
    }

    override fun getAllJobs(): List<GetJob> {
        if (jobDAO.count() > 0) {
            val jobList = this.jobDAO.findAll()
            val listTemp: MutableList<GetJob> = mutableListOf()
            for (job in jobList) {
                listTemp.add(
                    GetJob(
                        job.jobId,
                        job.jobTitle,
                        job.dateOfPosting,
                        job.lastDateToApply,
                        job.preferableSkills,
                        job.requiredExp,
                        job.recruiterEmail,
                        job.companyName,
                        job.companyAddress,
                        null,
                        null
                    )
                )
            }
            return listTemp

        } else {
            throw GetAllException("Job hasn't posted yet, let's wait till recruiter posts a job! ")
        }
    }

    override fun applyForJob(applicantEmail: String, getJob: GetJob): String {
        val applicantTemp = this.applicantDAO.findById(applicantEmail).get()
        val list = applicantTemp.appliedJobs?.toList()
        if (list != null) {
            for (applicant in list) {
                if (applicant.jobId == getJob.jobId) {
                    throw GetException("You already have Applied for this job,Goto Application Status to check your status")
                }
            }
        }
        if (getJob.lastDateToApply.isAfter(LocalDate.now())) {
            if (applicantDAO.existsByApplicantEmail(applicantEmail)) {
                if (jobDAO.existsById(getJob.jobId)) {
                    val statusId = ObjectId().toString()
                    //                      <---Added job to list which applicant has applied--->
                    applicantTemp.appliedJobs?.add(
                        GetJob(
                            getJob.jobId,
                            getJob.jobTitle,
                            getJob.dateOfPosting,
                            getJob.lastDateToApply,
                            getJob.preferableSkills,
                            getJob.requiredExp,
                            getJob.recruiterEmail,
                            getJob.companyName,
                            getJob.companyAddress,
                            LocalDate.now(),
                            statusId
                        )
                    )
                    this.applicantDAO.save(applicantTemp)

                    //                      <----- Increment in active applications--->
                    val jobTemp = this.jobDAO.findById(getJob.jobId).get()

                    jobTemp.activeApplications?.plus(1)

                    //                      <----- Added to Job DAO--->
                    jobTemp.appliedApplicants?.add(
                        GetApplicant(
                            applicantTemp.name,
                            applicantTemp.applicantEmail,
                            applicantTemp.phoneNumber,
                            applicantTemp.gender,
                            applicantTemp.education,
                            applicantTemp.instituteName,
                            applicantTemp.startYear,
                            applicantTemp.endYear,
                            applicantTemp.skills,
                            applicantTemp.jobProfile,
                            applicantTemp.numOfExp,
                            statusId
                        )
                    )
                    this.jobDAO.save(jobTemp)
                    //                      <----- changed job status to applied--->

                    val jobStatusTemp = JobStatus(
                        statusId,
                        null,
                        getJob.recruiterEmail,
                        applicantEmail,
                        getJob.jobId,
                        getJob.jobTitle,
                        getJob.companyName,
                        status = "applied"
                    )
                    this.jobStatusDAO.insert(jobStatusTemp)

//                        <---Added Applicant to list of job which recruiter  has posted--->


                    return "you have successfully applied for job:${getJob.jobTitle}"
                }else {
                    throw GetException(" job does not exist  ")
                }
            }else {
                throw GetException(" applicant does not exist ")
            }

        } else {
            throw GetException(" Last Date has been passed ")
        }

    }




override fun getApplicantById(applicantEmail: String): Applicant {
    if (applicantDAO.existsByApplicantEmail(applicantEmail)) {
        return this.applicantDAO.findById(applicantEmail).get()
    } else {
        throw GetException("Applicant doesn't exist with this email")
    }

}

override fun addApplicant(applicantDTO: ApplicantDTO): String {

    if (applicantDAO.existsByApplicantEmail(applicantDTO.applicantEmail)) {
        throw AddException("This email id is already been used,please use another")
    } else {
        val applicantNew = Applicant(
            Role.APPLICANT,
            applicantDTO.name,
            applicantDTO.applicantEmail,
            applicantDTO.password,
            applicantDTO.type,
            applicantDTO.phoneNumber,
            applicantDTO.gender,
            applicantDTO.education,
            applicantDTO.instituteName,
            applicantDTO.startYear,
            applicantDTO.endYear,
            applicantDTO.skills,
            applicantDTO.jobProfile,
            applicantDTO.numOfExp,
            null,
            isLoggedIn = false,
//                Base64.getEncoder().encodeToString(resume.bytes)

        )

        this.applicantDAO.insert(applicantNew)

        return "Applicant Successfully Registered"
    }
}

override fun addResume(resume: MultipartFile, applicantEmail: String): String {
    if (resumeDAO.existsByApplicantEmail(applicantEmail)) {
        throw GetException("You have already registered")
    } else {
        val resumeTemp = Resume(applicantEmail, Base64.getEncoder().encodeToString(resume.bytes))
        this.resumeDAO.insert(resumeTemp)
        return "Resume Uploaded Successfully"
    }
}

override fun getResume(applicantEmail: String): Resume {
    if (this.resumeDAO.existsByApplicantEmail(applicantEmail)) {
        return this.resumeDAO.findById(applicantEmail).get()
    } else {
        throw  GetException("Resume not found")
    }
}


override fun updateApplicant(applicantEmail: String, applicant: Applicant): String {
    if (applicantDAO.existsByApplicantEmail(applicantEmail)) {
        var applicantTemp = this.applicantDAO.findById(applicantEmail).get()
        applicantTemp = applicant
        applicantDAO.save(applicantTemp)
        return "Applicant successfully updated"


    } else throw GetException("Applicant doesn't exist")
}

override fun deleteApplicant(applicantEmail: String): String {
    if (applicantDAO.existsByApplicantEmail(applicantEmail)) {
        this.applicantDAO.deleteById(applicantEmail)

        return "Applicant deleted Successfully"
    } else throw DeleteException("Applicant doesn't exist")
}

override fun deleteJobStatus(applicantEmail: String, jobTitle: String): String {
    if (this.jobStatusDAO.existsByApplicantEmailAndJobTitle(applicantEmail, jobTitle)) {
        this.jobStatusDAO.deleteById(jobTitle)
        return "job status successfully deleted"
    } else throw  DeleteException("Job Status does not exist")
}

override fun getAppliedJob(applicantEmail: String): List<GetJob>? {
    return if (applicantDAO.existsByApplicantEmail(applicantEmail)) {
        val applicantTemp = this.applicantDAO.findById(applicantEmail).get()

        applicantTemp.appliedJobs?.toList()
    } else {
        throw GetException("")
    }
}

override fun getJobStatus(statusId: String): Any {
    if (this.jobStatusDAO.existsById(statusId)) {
        val jobStatusTemp = this.jobStatusDAO.findById(statusId).get()
        return jobStatusTemp.status!!
    } else {
        throw GetException("You haven't applied for this job yet")
    }
}

}