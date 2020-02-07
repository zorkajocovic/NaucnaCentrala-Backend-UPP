package com.example.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.LoginDto;
import com.example.dto.LoginRequestDto;
import com.example.model.Appuser;
import com.example.services.AppUserService;

@Service
public class CheckLoginService implements JavaDelegate {

	@Autowired
	private AppUserService userService;
	
	@Autowired
	private LoginDto loginDto; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FieldDto> userData = (List<FieldDto>) execution.getVariable("login");
		LoginRequestDto loggedUser = loginDto.convert(userData);

		/*Appuser user = userService.getbyUsername(loggedUser.getUsername());
		if(user != null) {
		   userService.setCurrentUser(user);
		}*/
	}

	
}
