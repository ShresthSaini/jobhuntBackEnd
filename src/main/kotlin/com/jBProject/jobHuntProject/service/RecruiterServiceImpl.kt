package com.jBProject.jobHuntProject.service

import com.jBProject.jobHuntProject.DAO.*
import com.jBProject.jobHuntProject.Exception.*
import com.jBProject.jobHuntProject.model.*
import org.springframework.stereotype.Service

@Service
class RecruiterServiceImpl(
    private var recruiterDAO: RecruiterDAO,
    private var jobDAO: JobDAO,
    private var applicantDAO: ApplicantDAO,
    private var jobStatusDAO: JobStatusDAO,


    private var sendEmailImpl: SendEmailImpl,
    private var applicantService: ApplicantService
) : RecruiterService {


    override fun addRecruiter(recruiterDTO: RecruiterDTO): String {
        if (recruiterDAO.existsByRecruiterEmail(recruiterDTO.recruiterEmail)) {
            throw AddException("This email id is already been used,please use another")
        } else {
            val recruiterNew = Recruiter(
                Role.RECRUITER,
                recruiterDTO.name,
                recruiterDTO.recruiterEmail,
                recruiterDTO.password,
                recruiterDTO.type,
                recruiterDTO.phoneNumber,
                recruiterDTO.gender,
                recruiterDTO.companyName,
                recruiterDTO.companyContactNumber,
                recruiterDTO.companyAddress,
                null,
                isLoggedIn = false,

                )

            this.recruiterDAO.insert(recruiterNew)
            return "Recruiter Successfully Registered"
        }

    }

    override fun addJob(jobDTO: JobDTO, recruiterEmail: String): String {
        if (jobDAO.existsById((jobDTO.jobId))) {
            throw AddException("This Job has been already posted by you ,please post another one")
        } else {
            val recruiterTemp = this.recruiterDAO.findById(recruiterEmail).get()
            val jobNew = Job(
                jobDTO.jobId,
                jobDTO.jobTitle,
                jobDTO.maxApplicants,
                jobDTO.maxPositions,
                0,
                jobDTO.dateOfPosting,
                jobDTO.lastDateToApply,
                null,
                jobDTO.preferableSkills,
                jobDTO.requiredExp,
                null,
                recruiterEmail,
                recruiterTemp.companyName,
                recruiterTemp.companyAddress

            )

            val recruiterNew = this.getRecruiterById(recruiterEmail)
            recruiterNew.jobsPosted?.add(jobNew)
            recruiterDAO.save(recruiterNew)

            this.jobDAO.insert(jobNew)

            return "You have  Successfully posted the job"
        }
    }

    override fun acceptedApplicants(jobId: String): List<GetApplicant> {
       if (this.jobDAO.existsById(jobId)){
           val jobTemp = this.jobDAO.findById(jobId).get()
           return jobTemp.acceptedCandidates!!.toList()
       }else {
           throw  GetException("job Doesn't Exist")
       }
    }



    override fun getRecruiterById(recruiterEmail: String): Recruiter {
        if (recruiterDAO.existsByRecruiterEmail(recruiterEmail)) {
            return this.recruiterDAO.findById(recruiterEmail).get()
        } else {
            throw GetException("Recruiter doesn't exist with this email")
        }
    }

    override fun getAllApplicants(): List<GetApplicant> {
        if (applicantDAO.count() > 0) {
            val getApplicantList = this.applicantDAO.findAll()

            val list: MutableList<GetApplicant> = mutableListOf()
            for (applicant in getApplicantList) {
                list.add(
                    GetApplicant(
                        applicant.name,
                        applicant.applicantEmail,
                        applicant.phoneNumber,
                        applicant.gender,
                        applicant.education,
                        applicant.instituteName,
                        applicant.startYear,
                        applicant.endYear,
                        applicant.skills,
                        applicant.jobProfile,
                        applicant.numOfExp,
                        null
                    )
                )
            }
            return list
        } else {
            throw  GetAllException("Applicants haven't registered yet")
        }
    }


    override fun callForInterview(interviewCall: InterviewCall): String {


        val subject = "Invitation to Interview "
        val body: String = "Dear Applicant: ${interviewCall.applicantName} ,\n" +
                "\n" +
                "Thank you for applying for the  job in Company: ${interviewCall.companyName}.\n" +
                "\n" +
                "We would like to invite you to come to our office to interview for the Position: ${interviewCall.jobTitle}. Your interview has been scheduled for ${interviewCall.timeAndDate}, at ${interviewCall.companyAddress}.\n" +
                "\n" +
                "Please call us at ${interviewCall.companyNumber}  or email me at ${interviewCall.recruiterEmail} if you have any questions or need to reschedule.\n" +
                "\n" +
                "Sincerely,\n" +
                "\n" +
                interviewCall.recruiterName

        sendEmailImpl.sendMail(interviewCall.applicantEmail, body, subject)
        return "Email Sent successfully"
    }


    override fun deleteRecruiter(recruiterEmail: String): String {
        if (recruiterDAO.existsByRecruiterEmail(recruiterEmail)) {
            this.recruiterDAO.deleteById(recruiterEmail)
            return "Recruiter deleted Successfully"
        } else
            throw GetException("Recruiter doesn't exist")
    }


    override fun setJobStatus(
        recruiterEmail: String,
        jobId: String,
        status: String,
        getApplicant: GetApplicant
    ): Any {
        if (jobStatusDAO.existsById(getApplicant.statusId!!)) {
            val jobTemp = this.jobDAO.findById(jobId).get()
            val jobStatusTemp = this.jobStatusDAO.findById(getApplicant.statusId!!).get()

            val recruiterTemp = this.getRecruiterById(recruiterEmail)
            jobStatusTemp.recruiterEmail = recruiterEmail
            jobStatusTemp.jobTitle = jobTemp.jobTitle
            jobStatusTemp.status = status
            jobStatusTemp.recruiterName = recruiterTemp.name
            jobStatusTemp.companyName = recruiterTemp.companyName
            jobStatusTemp.applicantEmail = getApplicant.applicantEmail
            jobStatusDAO.save(jobStatusTemp)

            when (status) {
                "accepted" -> {
                    jobTemp.acceptedCandidates?.add(getApplicant)
                    jobDAO.save(jobTemp)

                    return "Job status successfully set to accepted"
                }
                "rejected" -> {
                    val subject = "Application for the ${jobTemp.jobTitle} at ${recruiterTemp.companyName},\n"
                    val body: String =
                        "Dear Applicant: \n" +
                                "Thank you so much for your interest in the Position:  ${jobTemp.jobTitle} with ${recruiterTemp.companyName}. We appreciate you taking the time to Apply for the job.\n" +
                                "\n" + " we have to inform you that this time we won’t be able to invite you to the next phase of our selection process." +
                                "\n" + "We wish you every personal and professional success in your future endeavors.\n" +

                                "Kind regards, \n" + recruiterTemp.name


                    return sendEmailImpl.sendMail(getApplicant.applicantEmail, body, subject)

                }
                else -> {
                    throw GetException("either select accepted or rejected")

                }
            }
        } else {
            throw GetException("You haven't posted this job")
        }

    }

    override fun getJobPosted(recruiterEmail: String): List<Job>? {
        return if (recruiterDAO.existsByRecruiterEmail(recruiterEmail)) {
            val recruiterTemp: Recruiter = this.recruiterDAO.findById(recruiterEmail).get()
            recruiterTemp.jobsPosted?.toList()
        } else {
            throw GetException(" you haven't posted a job yet")
        }
    }

    override fun getJobStatusById(jobTitle: String): JobStatus {
        if (jobStatusDAO.existsByJobTitle(jobTitle)) {
            return this.jobStatusDAO.findById(jobTitle).get()
        } else {
            throw GetException("Couldn't find job Status")
        }
    }

    override fun deleteJobById(recruiterEmail: String, jobId: String): String {
        if (this.jobDAO.existsById(jobId)) {
            val recruiterTemp = this.recruiterDAO.findById(recruiterEmail).get()
            val jobTemp = this.jobDAO.findById(jobId).get()

//            val index  = recruiterTemp.jobsPosted?.indexOf(jobTemp)
//            if (index != null) {
//                recruiterTemp.jobsPosted!!.removeAt(index)
//            }
            recruiterTemp.jobsPosted?.remove(jobTemp)
            recruiterDAO.save(recruiterTemp)
            this.jobDAO.deleteById(jobId)

            return "Job successfully deleted"
        } else {
            throw DeleteException("Couldn't find the job")
        }
    }

    override fun getAppliedApplicants(jobId: String): Any {

        return if (jobDAO.existsById(jobId)) {
            val jobTemp = this.jobDAO.findById(jobId).get()

                jobTemp.appliedApplicants!!.toList()

        } else {
            throw GetException("Applicant has not applied for this job")
        }
    }

    override fun updateJob(jobId: String, job: Job): String {
        return if (jobDAO.existsById(jobId)) {
            var jobTemp = this.jobDAO.findById(jobId).get()

            jobTemp = job

            jobDAO.save(jobTemp)

            "Job updated Successfully"
        } else {
            throw GetException("  Updation Unsuccessful, Job doesn't exist with this Job ID ")
        }
    }


    override fun updateRecruiter(recruiterEmail: String, recruiter: Recruiter): String {
        return if (recruiterDAO.existsByRecruiterEmail(recruiterEmail)) {
            var recruiterTemp = this.recruiterDAO.findById(recruiterEmail).get()
            recruiterTemp = recruiter
            recruiterDAO.save(recruiterTemp)
            "Recruiter ${recruiterTemp.name} updated Successfully"
        } else
            throw GetException("Recruiter doesn't exist")
    }
}
