package com.example.camunda.services;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Appuser;
import com.example.services.AppUserService;

@Service
public class RoleSetter implements JavaDelegate{
	
	@Autowired
	private AppUserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		boolean approved = (boolean)execution.getVariable("approve");
		
		if(approved) {
			String email = execution.getVariable("userId").toString();
			Appuser user = userService.getbyEmail(email);
			user.setRole("Editor");
			userService.updateUser(user);
		}
		
	}

}
