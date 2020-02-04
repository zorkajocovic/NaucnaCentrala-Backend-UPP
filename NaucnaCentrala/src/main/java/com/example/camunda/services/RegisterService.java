package com.example.camunda.services;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.camunda.dto.AddSciFieldDto;
import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.RegisterDto;
import com.example.dto.AppUserDto;
import com.example.enums.Roles;
import com.example.model.Appuser;
import com.example.model.ScientificField;
import com.example.services.AppUserService;

@Service
public class RegisterService implements JavaDelegate {
	
	@Autowired
	private RegisterDto registerDto;
	
	@Autowired
	private AppUserService userService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private AddSciFieldDto sciFieldDto;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FieldDto> userData = (List<FieldDto>) execution.getVariable("registration");
		List<FieldDto> sciFields = (List<FieldDto>) execution.getVariable("sciFields");
		
		AppUserDto createdUser = registerDto.convert(userData);
		createdUser.setRole(Roles.USER.toString());
		Appuser newUser = userService.mapDTO(createdUser);
		newUser.setScientificFields(sciFieldDto.convert(sciFields));
		
        User camundaUser = identityService.newUser(createdUser.getUsername());
        camundaUser.setEmail(createdUser.getEmail());
        camundaUser.setFirstName(createdUser.getName());
        camundaUser.setLastName(createdUser.getSurname());
        camundaUser.setPassword(createdUser.getPassword());
        identityService.saveUser(camundaUser);

		if(createdUser != null){
			 userService.addUser(newUser);
		}		
	}
}
