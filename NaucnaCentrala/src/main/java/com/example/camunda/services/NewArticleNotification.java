package com.example.camunda.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.email.EmailService;
import com.example.model.Appuser;
import com.example.model.Magazine;
import com.example.services.AppUserService;
import com.example.services.MagazineService;

@Service
public class NewArticleNotification implements JavaDelegate {

	@Autowired
	EmailService emailService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	AppUserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) execution.getVariable("newArticle"); 
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());

		Long magazineId = (Long) map.get("magazineId");
		Magazine magazine = magazineService.getMagazine(magazineId);
		
		Appuser main_editor = magazine.getAppuser();
		Appuser author = userService.getOne(authorId);
		
		execution.setVariable("mainEditor", main_editor.getUsername());
		
		 String body = "Hello,\\r\\n" +
	         			"New article is submited.";
		
		emailService.getMail().setTo(author.getEmail());
		emailService.getMail().setSubject("New Article in progress");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		
		emailService.getMail().setTo(main_editor.getEmail());
		emailService.getMail().setSubject("New Article in progress");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		
	}

	

}