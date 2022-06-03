package com.jBProject.jobHuntProject.services


import com.jBProject.jobHuntProject.DAO.JobDAO
import com.jBProject.jobHuntProject.DAO.RecruiterDAO
import com.jBProject.jobHuntProject.Exception.AddException
import com.jBProject.jobHuntProject.Exception.DeleteException
import com.jBProject.jobHuntProject.Exception.GetException
import com.jBProject.jobHuntProject.model.*
import com.jBProject.jobHuntProject.service.ApplicantService
import com.jBProject.jobHuntProject.service.RecruiterService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Java6Assertions.assertThat

import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

import java.util.*

@SpringBootTest
internal class BasicGeneralImplTest @Autowired constructor(
    var applicantService: ApplicantService,
    var recruiterService: RecruiterService,
    var jobDAO: JobDAO,
    var recruiterDAO: RecruiterDAO

) {
    @Test
    fun basicGeneralOps() {
//   ******************************     Add Applicant      ****************************************
        assertThat(
        this.applicantService.addApplicant(
            ApplicantDTO(
                "test", "test2@test.com", "test123",
                "Applicant", "9999999999", "male", "testEducation", "testInstitute",
                Date(2202 / 1 / 16), Date(2202 / 2 / 16), mutableListOf("test", "test2"), "testProfile", 0,
            )
        )).isEqualTo("Applicant Successfully Registered")


        val applicant = this.applicantService.getApplicantById("test2@test.com")
        assertThat(applicant).isNotNull
//
        assertThrows<AddException>(message = "This email id is already been used,please use another") {
            this.applicantService.addApplicant(
                ApplicantDTO(
                    applicant.name,
                    applicant.applicantEmail,
                    applicant.password,
                    applicant.type,
                    applicant.phoneNumber,
                    applicant.gender,
                    applicant.education,
                    applicant.instituteName,
                    applicant.startYear,
                    applicant.endYear,
                    applicant.skills,
                    applicant.jobProfile,
                    applicant.numOfExp

                ),
            )
        }
//   ******************************     Add Recruiter     ****************************************
        assertThat(
        this.recruiterService.addRecruiter(
            RecruiterDTO(
                "test",
                "test@test.com",
                "Password",
                "Recruiter",
                "0000000000",
                "MMMM",
                "Company",
                "0000000000",
                "City"
            )
        )).isEqualTo("Recruiter Successfully Registered")

        val recruiter = this.recruiterService.getRecruiterById("test@test.com")
        assertThat(recruiter).isNotNull

        assertThrows<AddException>(message = "This email id is already been used,please use another"){
            this.recruiterService.addRecruiter(RecruiterDTO(recruiter.name,recruiter.recruiterEmail,recruiter.password,"Recruiter",recruiter.phoneNumber,recruiter.gender,recruiter.companyName,recruiter.companyContactNumber,recruiter.companyAddress))
        }
//
////   ******************************     update Recruiter and applicant   ****************************************
        assertThat(this.recruiterService.updateRecruiter(recruiter.recruiterEmail, recruiter)).isEqualTo("Recruiter ${recruiter.name} updated Successfully")

        assertThat(this.applicantService.updateApplicant(applicant.applicantEmail, applicant)).isEqualTo("Applicant successfully updated")
//
////   ******************************    Recruiter Adds a job      ****************************************
        assertThat(this.recruiterService.addJob(
            JobDTO(
                "abc123",
                "test job",
                10,
                2,
                "1111-11-01",
                LocalDate.parse("3333-03-03"),
                mutableListOf("skill1", "skill2"),
                5,
            ), recruiter.recruiterEmail
        )).isEqualTo("You have  Successfully posted the job")

        val job = this.applicantService.getJobById("abc123")
        assertThat(job).isNotNull
////   ******************************     update job   ****************************************
        assertThat(this.recruiterService.updateJob(job.jobId,job)).isEqualTo("Job updated Successfully")
//
//
////   ******************************     Get Jobs      ****************************************
//        assertThat(this.applicantService.getAllJobs()).isEqualTo(this.jobDAO.findAll())
//
//   ******************************     Apply for job      ****************************************
        assertThat(
        this.applicantService.applyForJob(
            applicant.applicantEmail,  GetJob(
                job.jobId,
                job.jobTitle,
                job.dateOfPosting,
                job.lastDateToApply,
                job.preferableSkills,
                job.requiredExp,
                recruiter.recruiterEmail,
                recruiter.companyName,
                recruiter.companyAddress,
                LocalDate.parse("2022-02-16"),
                "def456"

            )
        )).isEqualTo("you have successfully applied for job:${job.jobTitle}")
//
////   ******************************     getJobPosted      ****************************************
//        assertThat(this.recruiterService.getJobPosted(recruiter.recruiterEmail)).isEqualTo(recruiter.jobsPosted?.toList())
//////   ******************************     setJobStatus      ****************************************
//        this.recruiterService.setJobStatus(
//            recruiter.recruiterEmail, job.jobId, "accepted",
//            GetApplicant(
//                applicant.name,
//                applicant.applicantEmail,
//                applicant.phoneNumber,
//                applicant.gender,
//                applicant.education,
//                applicant.instituteName,
//                applicant.startYear,
//                applicant.endYear,
//                applicant.skills,
//                applicant.jobProfile,
//                applicant.numOfExp,
//                "def456"
//            )
//        )
//////   ******************************     getJobStatus       *****************************************
////        this.applicantService.getJobStatus(applicant.applicantEmail,job.jobTitle)
////   ******************************     Delete User and job      *****************************************
////        assertThat(this.applicantService.deleteJobStatus(applicant.applicantEmail,job.jobTitle)).isEqualTo("job status successfully deleted")
        assertThat(
            this.recruiterService.deleteJobById(
                recruiter.recruiterEmail,
                job.jobId
            )
        ).isEqualTo("Job successfully deleted")
        assertThat(this.applicantService.deleteApplicant(applicant.applicantEmail)).isEqualTo("Applicant deleted Successfully")
        assertThat(this.recruiterService.deleteRecruiter(recruiter.recruiterEmail)).isEqualTo("Recruiter deleted Successfully")
    }

//    @Test
//    fun deleteData() {
//        assertThat(
//            this.recruiterService.deleteJobById(
//                "test@test.com",
//                "abc123"
//            )
//        ).isEqualTo("Job successfully deleted")
//        assertThat(this.applicantService.deleteApplicant("test2@test.com")).isEqualTo("Applicant deleted Successfully")
//        assertThat(this.recruiterService.deleteRecruiter("test@test.com")).isEqualTo("Recruiter deleted Successfully")
//    }

    @Test
    fun `should get Not Found for invalid email for find`() {
        var exception = assertThrows<GetException>(message = "Applicant doesn't exist with this email") {
            this.applicantService.getApplicantById("test2@test.com")
        }
    }

    @Test
    fun `should get not found for invalid email on updating`() {
        var exception = assertThrows<GetException>(message = "Applicant doesn't exist") {
            this.applicantService.updateApplicant(
                "test2@gmail.com", Applicant(
                    Role.APPLICANT,
                    "TEST3",
                    "test2@test.com",
                    "test125",
                    "Applicant",
                    "11111111111",
                    "male",
                    "testEducation",
                    "testInstitute",
                    Date(2202 / 1 / 16),
                    Date(2202 / 2 / 16),
                    mutableListOf("test", "test2"),
                    "testProfile",
                    5,
                    mutableListOf(
                        GetJob(
                            "abc123",
                            "test job",
                            "1111-11-01",
                            LocalDate.parse("3333-03-03"),
                            mutableListOf("skill1", "skill2"),
                            5,
                            "test@test.com",
                            "Company",
                            "City",
                            LocalDate.parse("3333-01-01"),
                            "def456"
                        )
                    ),
                    false,

                )
            )
        }
    }

    @Test
    fun `should get not found for deleting`() {
        var exception = assertThrows<DeleteException>( "Applicant doesn't exist") {
            this.applicantService.deleteApplicant("test2@gmail.com")
        }
    }


}