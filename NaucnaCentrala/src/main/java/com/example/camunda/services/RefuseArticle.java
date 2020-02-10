package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;

@Service
public class RefuseArticle implements JavaDelegate {

	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String userEmail = execution.getVariable("authorEmail").toString();
		
        String body = "Hello, " +
         				"Your article rejected because of technical reasons. Please try again. Good Luck!";
        
		emailService.getMail().setTo(userEmail);
		emailService.getMail().setSubject("Rejecting article");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
	}
}
