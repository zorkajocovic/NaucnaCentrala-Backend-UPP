package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Appuser;
import com.example.model.Magazine;
import com.example.model.Subscription;
import com.example.repositories.SubscriptionRepository;
import com.example.services.AppUserService;
import com.example.services.MagazineService;

@Service
public class AddSubscription implements JavaDelegate {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Autowired 
	AppUserService userService;
	
	@Autowired
	MagazineService magService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
	
		Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
		Long magazineId = Long.parseLong(execution.getVariable("magazineId").toString());

		Appuser user = userService.getOne(authorId);
		Magazine magazine = magService.getMagazine(magazineId);
		
		Subscription sub = new Subscription();
		sub.setPrice(20);
		sub.setAppuser(user);
		sub.setMagazine(magazine);
		
		subscriptionRepository.save(sub);
		
		execution.setVariable("subcribed", true);
	}
}

