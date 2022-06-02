package com.jBProject.jobHuntProject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class SendEmailImpl(@Autowired var mailSenderImpl: JavaMailSenderImpl) {

    fun sendMail(toMail:String,body:String,subject:String){
        val message:SimpleMailMessage = SimpleMailMessage()
        message.setFrom("job.hunt034@gmail.com")
        message.setTo(toMail)
        message.setText(body)
        message.setSubject(subject)
        mailSenderImpl.send(message)
    }
}