package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;

@Service
public class WrongFormat implements JavaDelegate {
	
	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String userEmail = execution.getVariable("authorEmail").toString();
		String fixFormat = execution.getVariable("fixFormat").toString();

		String body = "Hello, " +
         		"Your article does not have good format! Please fix it till the " + fixFormat;
        
		emailService.getMail().setTo(userEmail);
		emailService.getMail().setSubject("Fix PDF article");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
	}

}
