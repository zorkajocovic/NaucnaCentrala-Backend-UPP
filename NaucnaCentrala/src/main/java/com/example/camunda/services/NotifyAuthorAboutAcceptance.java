package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;

@Service
public class NotifyAuthorAboutAcceptance implements JavaDelegate {
	
	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String userEmail = execution.getVariable("authorEmail").toString();
		
		 String body = "Hello, " +
	         			"Your article is accepted.";
		
		emailService.getMail().setTo(userEmail);
		emailService.getMail().setSubject("Article Acceptance");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		
	}

}
