package com.example.camunda.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.tags.EditorAwareTag;

import com.example.dto.Roles;
import com.example.dto.SciFieldDto;
import com.example.email.EmailService;
import com.example.model.Appuser;
import com.example.model.Magazine;
import com.example.model.ScientificField;
import com.example.services.AppUserService;
import com.example.services.MagazineService;
import com.example.services.SciFieldService;

@Service
public class ChooseEditorOfSciArea implements JavaDelegate {

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	SciFieldService sciService;
	
	@Autowired
	AppUserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Long magazineId = (Long) execution.getVariable("magazineId");
		Magazine magazine = magazineService.getMagazine(magazineId);
		Set<Appuser> editorSciField = new HashSet<Appuser>();
	
		for(ScientificField sci : magazine.getScientificFields()){
			for(Appuser user : userService.getAll()){
				if(user.getScientificFields().contains(sci) && user.getRole().equals(Roles.EDITOR.toString()))
					editorSciField.add(user);
			}
		}	
		List<Appuser> editorsForSciField = new ArrayList<>(editorSciField);
		
		if(editorSciField.size() > 0) {
		String body = "Hello, " +
  						"You have a new article for review. Please choose 2 reviewrs for reviewing.";
 
		emailService.getMail().setTo(editorsForSciField.get(0).getEmail());
		emailService.getMail().setSubject("Choose reviewers");
		emailService.getMail().setText(body);
		emailService.sendNotificaitionSync();
		
		execution.setVariable("assignedUser", editorsForSciField.get(0).getUsername());
		}
		else {
			execution.setVariable("assignedUser", userService.getCurrentUser().getUsername());
		}
	}


}
