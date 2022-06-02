//package com.jBProject.jobHuntProject.services
//
//
//import com.jBProject.jobHuntProject.DAO.GetJobDAO
//import com.jBProject.jobHuntProject.Exception.AddException
//import com.jBProject.jobHuntProject.Exception.DeleteException
//import com.jBProject.jobHuntProject.Exception.GetException
//import com.jBProject.jobHuntProject.model.*
//import com.jBProject.jobHuntProject.service.ApplicantService
//import com.jBProject.jobHuntProject.service.RecruiterService
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.assertj.core.api.Java6Assertions.assertThat
//
//import org.junit.jupiter.api.assertThrows
//import java.time.LocalDate
//
//import java.util.*
//
//@SpringBootTest
//internal class BasicGeneralImplTest @Autowired constructor(
//    var getJobDAO: GetJobDAO,
//    var applicantService: ApplicantService,
//    var recruiterService: RecruiterService
//
//) {
//    @Test
//    fun basicGeneralOps() {
////   ******************************     Add Applicant      ****************************************
//
//        this.applicantService.addApplicant(
//            ApplicantDTO(
//                "test", "test2@test.com", "test123",
//                "Applicant", "9999999999", "male", "testEducation", "testInstitute",
//                Date(2202 / 1 / 16), Date(2202 / 2 / 16), mutableListOf("test", "test2"), "testProfile", 0,
//            )
//        )
//
//
//        val applicant = this.applicantService.getApplicantById("test2@test.com")
//        assertThat(applicant).isNotNull
//
//        assertThrows<AddException>(message = "This email id is already been used,please use another") {
//            this.applicantService.addApplicant(
//                ApplicantDTO(
//                    applicant.name,
//                    applicant.applicantEmail,
//                    applicant.password,
//                    applicant.type,
//                    applicant.phoneNumber,
//                    applicant.gender,
//                    applicant.education,
//                    applicant.instituteName,
//                    applicant.startYear,
//                    applicant.endYear,
//                    applicant.skills,
//                    applicant.jobProfile,
//                    applicant.numOfExp
//
//                ),
//            )
//        }
////   ******************************     Add Recruiter     ****************************************
//        this.recruiterService.addRecruiter(
//            RecruiterDTO(
//                "test",
//                "test@test.com",
//                "Password",
//                "Recruiter",
//                "0000000000",
//                "MMMM",
//                "Company",
//                "0000000000",
//                "City"
//            )
//        )
//        val recruiter = this.recruiterService.getRecruiterById("test@test.com")
//        assertThat(recruiter).isNotNull
//
////   ******************************     update Recruiter and applicant   ****************************************
//        assertThat(this.recruiterService.updateRecruiter(recruiter.recruiterEmail, recruiter))
//
//        assertThat(this.applicantService.updateApplicant(applicant.applicantEmail, applicant))
//
//
////   ******************************    Recruiter Adds a job      ****************************************
//        this.recruiterService.addJob(
//            JobDTO(
//                "testJob10",
//                100,
//                200,
//                Date(2222 / 2 / 22),
//                LocalDate.parse("2023-03-03"),
//                mutableListOf("skill1", "skill2"),
//                5,
//            ), recruiter.recruiterEmail
//        )
//
//        val job = this.applicantService.getJobById("testJob10")
//        assertThat(job).isNotNull
//
////   ******************************     Get Jobs      ****************************************
////        assertThat(this.applicantService.getAllJobs()).isEqualTo(getJobDAO.findAll())
//
////   ******************************     Apply for job      ****************************************
//        this.applicantService.applyForJob(
//            applicant.applicantEmail,  GetJob(
//                "testJob10",
//                Date(2222 / 2 / 22),
//                LocalDate.parse("2023-03-03"),
//                mutableListOf("skill1", "skill2"),
//                5,
//                recruiter.recruiterEmail,
//                recruiter.companyName,
//                recruiter.companyAddress,
//                null
//            )
//        )
//
////   ******************************     getJobPosted      ****************************************
////        this.recruiterService.getJobPosted(recruiter.recruiterEmail)
//////   ******************************     setJobStatus      ****************************************
//        this.recruiterService.setJobStatus(
//            recruiter.recruiterEmail, job.jobTitle, "accepted",
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
//                applicant.numOfExp
//            )
//        )
//////   ******************************     getJobStatus       *****************************************
////        this.applicantService.getJobStatus(applicant.applicantEmail,job.jobTitle)
////   ******************************     Delete User and job      *****************************************
//        assertThat(
//            this.recruiterService.deleteJobById(
//                recruiter.recruiterEmail,
//                job.jobTitle
//            )
//        ).isEqualTo("Job successfully deleted")
////        assertThat(this.applicantService.deleteJobStatus(applicant.applicantEmail,job.jobTitle)).isEqualTo("job status successfully deleted")
//        assertThat(this.applicantService.deleteApplicant(applicant.applicantEmail)).isEqualTo("Applicant deleted Successfully")
//        assertThat(this.recruiterService.deleteRecruiter(recruiter.recruiterEmail)).isEqualTo("Recruiter deleted Successfully")
//    }
//
////    @Test
////    fun deleteData() {
////        assertThat(
////            this.recruiterService.deleteJobById(
////                "test@test.com",
////                "testJob10"
////            )
////        ).isEqualTo("Job successfully deleted")
////        assertThat(this.applicantService.deleteApplicant("test2@test.com")).isEqualTo("Applicant deleted Successfully")
////        assertThat(this.recruiterService.deleteRecruiter("test@test.com")).isEqualTo("Recruiter deleted Successfully")
////    }
//
//    @Test
//    fun `should get Not Found for invalid email for find`() {
//        var exception = assertThrows<GetException>(message = "Applicant doesn't exist with this email") {
//            this.applicantService.getApplicantById("test2@test.com")
//        }
//    }
//
//    @Test
//    fun `should get not found for invalid email on updating`() {
//        var exception = assertThrows<GetException>(message = "Applicant doesn't exist") {
//            this.applicantService.updateApplicant(
//                "test2@gmail.com", Applicant(
//                    Role.APPLICANT,
//                    "TEST3",
//                    "test2@test.com",
//                    "test125",
//                    "Applicant",
//                    "11111111111",
//                    "male",
//                    "testEducation",
//                    "testInstitute",
//                    Date(2202 / 1 / 16),
//                    Date(2202 / 2 / 16),
//                    mutableListOf("test", "test2"),
//                    "testProfile",
//                    5,
//                    mutableListOf(
//                        GetJob(
//                            "testJob",
//                            Date(2222 / 2 / 22),
//                            LocalDate.parse("2007-12-03"),
//                            mutableListOf("skill1", "skill2"),
//
//                            5,
//                            "test@test.com",
//                            "Company",
//                            "City",
//                            null
//
//
//                        )
//                    ),
//                    false,
//
//                )
//            )
//        }
//    }
//
//    @Test
//    fun `should get not found for deleting`() {
//        var exception = assertThrows<DeleteException>( "Applicant doesn't exist") {
//            this.applicantService.deleteApplicant("test2@gmail.com")
//        }
//    }
//
//
//}