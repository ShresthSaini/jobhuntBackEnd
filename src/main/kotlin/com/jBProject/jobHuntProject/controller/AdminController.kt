package com.jBProject.jobHuntProject.controller


import com.jBProject.jobHuntProject.model.Admin
import com.jBProject.jobHuntProject.model.Recruiter
import com.jBProject.jobHuntProject.model.AdminDTO
import com.jBProject.jobHuntProject.service.AdminService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000/")
class AdminController(var adminService: AdminService) {

    @PostMapping("/addAdmin")
    fun addAdmin(@Valid @RequestBody adminDTO: AdminDTO):String = this.adminService.addAdmin(adminDTO)

    @GetMapping("/{email}")
    fun getAdmin(@PathVariable email:String):Admin = this.adminService.getAdminById(email)

    @PutMapping("/updateAdmin")
    fun updateAdmin(@RequestBody adminDTO: AdminDTO):Admin = this.adminService.updateAdmin(adminDTO)

    @DeleteMapping("/{email}")
    fun deleteAdmin(@PathVariable email:String):String = this.adminService.deleteAdmin(email)

//    @PostMapping("/contactUs")
//    fun contactUs(@RequestBody contactUs: ContactUs ):String = this.adminService.contactMsg(contactUs)
//
//    @GetMapping("/getMesgReceived")
//    fun getMsgReceived():List<ContactUs>? = this.adminService.getMesgReceived()

    @GetMapping("/getAllRecruiters")
    fun getAllRecruiters():List<Recruiter> = this.adminService.getAllRecruiters()

    @GetMapping("/getAllAdmins")
    fun getAllAdmins():List<Admin> = this.adminService.getAllAdmins()
}