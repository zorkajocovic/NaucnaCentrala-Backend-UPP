package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Magazine;
import com.example.repositories.MagazineRepository;
import com.example.services.MagazineService;

@Service
public class CheckMagazinesAccess implements JavaDelegate {
	
    @Autowired
    private MagazineService magazineService;
	  
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Long magazineId = Long.parseLong(execution.getVariable("magazineId").toString());
		Magazine magazine = magazineService.getMagazine(magazineId);
		
		if(magazine != null) {
			if(magazine.getIsopenaccess())
				execution.setVariable("isOpenAccess", true);
			else
				execution.setVariable("isOpenAccess", false);
		}
	}
}