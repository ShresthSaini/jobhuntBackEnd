package com.jBProject.jobHuntProject.controller

import com.jBProject.jobHuntProject.model.*

import com.jBProject.jobHuntProject.service.ApplicantService


import org.springframework.web.bind.annotation.*

import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile

import javax.validation.Valid

@RestController
@RequestMapping("/Applicant")
@CrossOrigin("https://frontend-jobhunt.herokuapp.com/")
class ApplicantController(private var applicantService: ApplicantService) {


    @PostMapping("/addApplicant")
    fun addApplicant(@RequestBody @Valid applicant: ApplicantDTO): String =
        this.applicantService.addApplicant(applicant)

    @PostMapping(
        value = ["/addResume"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addResume(@RequestParam resume: MultipartFile, applicantEmail: String): String =
        this.applicantService.addResume(resume, applicantEmail)

    @GetMapping("/getResume/{applicantEmail}")
    fun getResume(@PathVariable applicantEmail: String):Resume = this.applicantService.getResume(applicantEmail)


//    @PostMapping(
//        value = ["/add"],
//        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE]
//    )
//    fun addApplicant( @Valid applicant: ApplicantDTO):Applicant{
//        return this.applicantService.addApplicant(
//            applicant.name,
//            applicant.applicantEmail,
//            applicant.password,
//            applicant.type,
//            applicant.phoneNumber,
//            applicant.gender,
//            applicant.education,
//            applicant.instituteName,
//            applicant.startYear,
//            applicant.endYear,
//            applicant.skills,
//            applicant.jobProfile,
//            applicant.numOfExp,
//            applicant.resume,
//               applicant
//
//        )
//    }


    @PostMapping("/applyForJob/{applicantEmail}")
    fun applyForJob(@PathVariable applicantEmail: String, @RequestBody getJob: GetJob) =
        this.applicantService.applyForJob(
            applicantEmail, getJob
        )

    @GetMapping("/getAllJobs")
    fun getAllJobs(): List<GetJob> = this.applicantService.getAllJobs()


    @PutMapping("/update{applicantEmail}")
    fun updateRecruiter(@PathVariable applicantEmail: String, @RequestBody applicant: Applicant): String =
        this.applicantService.updateApplicant(applicantEmail, applicant)

    @DeleteMapping("/delete{applicantEmail}")
    fun deleteApplicant(@PathVariable applicantEmail: String): String =
        this.applicantService.deleteApplicant(applicantEmail)

    @GetMapping("/getAppliedJobs{applicantEmail}")
    fun getAppliedJobs(@PathVariable applicantEmail: String) = this.applicantService.getAppliedJob(applicantEmail)

    @GetMapping("/getById{applicantEmail}")
    fun getApplicantById(@PathVariable applicantEmail: String): Applicant =
        this.applicantService.getApplicantById(applicantEmail)

    @GetMapping("/getJobStatus{statusId}")
    fun getJobStatus(@PathVariable statusId: String): Any =
        this.applicantService.getJobStatus(statusId)


}