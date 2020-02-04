package com.example.camunda.services;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;

@Service
public class RegisterConfirmationEmail implements JavaDelegate {

	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Registered mejl");
		String userEmail = execution.getVariable("userEmail").toString();
		//String token = UUID.randomUUID().toString();
        String confirmationUrl = "regitrationConfirm?processInstanceId=" + execution.getProcessInstanceId();
        String body = "Hello," +
         		"You have successfully submitted your registration. Please click on link below and "
         		+ "verify your email address in order to complete the registration.\r\n" + 
         		"http://localhost:8090/api/user/" + confirmationUrl;
        
		emailService.getMail().setTo(userEmail);
		emailService.getMail().setSubject("Registration confirmation");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		execution.setVariable("userEmail", userEmail);

	}
}
