package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class AssignDOI implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Pocelo je indeksiranjeeee ...");
	}


	
}
