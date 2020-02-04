package com.example.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.camunda.dto.FieldDto;
import com.example.camunda.dto.RegisterDto;
import com.example.dto.AppUserDto;

@Service
public class RegistrerCheckData implements JavaDelegate {
	
	@Autowired
	private RegisterDto registerDto;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
	
		List<FieldDto> registerData = (List<FieldDto>) execution.getVariable("registration");
		List<FieldDto> sciFields = (List<FieldDto>) execution.getVariable("sciFields"); 

		AppUserDto userData = registerDto.convert(registerData);	
		if(userData.getEmail() != null && userData.getPassword() != null && sciFields.size() > 0) {
			execution.setVariable("checked", true);
			execution.setVariable("userId", userData.getId());
			execution.setVariable("userEmail", userData.getEmail());
			execution.setVariable("isReviewer", userData.isReviewer());
			execution.setVariable("noSciFields", userData.getNoOfSciFields());
		}
		else
			execution.setVariable("checked", false);
	}
}
