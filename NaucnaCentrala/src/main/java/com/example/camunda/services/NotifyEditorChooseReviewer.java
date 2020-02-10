package com.example.camunda.services;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;
import com.example.model.Appuser;
import com.example.model.Magazine;

@Service
public class NotifyEditorChooseReviewer implements JavaDelegate {
	
	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
    	String editorEmail = execution.getVariable("editorEmail").toString();
		
		 String body = "Hello, " +
	         			"You have to choose another reviewer.";
		
		emailService.getMail().setTo(editorEmail);
		emailService.getMail().setSubject("New Article in progress");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		
	}

}
