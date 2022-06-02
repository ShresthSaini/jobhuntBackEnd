package com.jBProject.jobHuntProject.controller

import com.jBProject.jobHuntProject.model.*

import com.jBProject.jobHuntProject.service.RecruiterService
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/Recruiter")
@CrossOrigin("http://localhost:3000/")
class RecruiterController(private var recruiterService: RecruiterService) {

    @PostMapping("/addRecruiter")
    fun addRecruiter(@Valid @RequestBody recruiter: RecruiterDTO) = this.recruiterService.addRecruiter(recruiter)

    @PostMapping("/addJob/{recruiterEmail}")
    fun addJob(@PathVariable recruiterEmail: String, @Valid @RequestBody jobDTO: JobDTO): String =
        this.recruiterService.addJob(jobDTO, recruiterEmail)


    @GetMapping("/acceptedApplicants/{jobId}")
    fun acceptedApplicants(@PathVariable jobId: String):List<GetApplicant> = this.recruiterService.acceptedApplicants(jobId)

    @PostMapping("/interviewCall")
    fun callForInterview(@RequestBody interviewCall: InterviewCall): String =
        this.recruiterService.callForInterview(interviewCall)

    @PutMapping("/update/{recruiterEmail}")
    fun updateRecruiter(@PathVariable recruiterEmail: String, @RequestBody recruiter: Recruiter): String =
        this.recruiterService.updateRecruiter(recruiterEmail, recruiter)

    @DeleteMapping("/delete{recruiterEmail}")
    fun deleteRecruiter(@PathVariable recruiterEmail: String): String =
        this.recruiterService.deleteRecruiter(recruiterEmail)

    @PutMapping("/setStatus/{recruiterEmail}/{jobId}/{status}")
    fun setJobStatus(
        @PathVariable recruiterEmail: String,
        @PathVariable jobId: String,
        @PathVariable status: String,
        @RequestBody getApplicant: GetApplicant
    ): Any = this.recruiterService.setJobStatus(
        recruiterEmail, jobId, status,getApplicant
    )

    @GetMapping("/getAppliedApplicants/{jobId}")
    fun getAppliedApplicants(@PathVariable jobId: String): Any =
        this.recruiterService.getAppliedApplicants(jobId)

    @GetMapping("/getAllApplicants")
    fun getAllApplicants(): List<GetApplicant> {
        return this.recruiterService.getAllApplicants()
    }

    @PutMapping("/updateJob/{jobId}")
    fun updateJob(@PathVariable jobId:String, @RequestBody job: Job): String =
        this.recruiterService.updateJob(jobId, job)

    @GetMapping("/getJobPosted/{recruiterEmail}")
    fun getJobPosted(@PathVariable recruiterEmail: String): List<Job>? =
        this.recruiterService.getJobPosted(recruiterEmail)

    @DeleteMapping("/deleteJob/{recruiterEmail}/{jobId}")
    fun deleteJob(@PathVariable recruiterEmail: String ,jobId: String ):String = this.recruiterService.deleteJobById(recruiterEmail,jobId)

    @GetMapping("/getById/{recruiterEmail}")
    fun getRecruiterById(@PathVariable recruiterEmail: String) = this.recruiterService.getRecruiterById(recruiterEmail)

}