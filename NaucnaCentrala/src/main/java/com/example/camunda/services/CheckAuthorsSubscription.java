package com.example.camunda.services;

import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Subscription;
import com.example.repositories.SubscriptionRepository;

@Service
public class CheckAuthorsSubscription implements JavaDelegate {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

    	String authorId = execution.getVariable("authorId").toString();
		//Long magazineId = Long.parseLong(execution.getVariable("magazineId").toString());

    	Subscription subscription = subscriptionRepository.findOneByAppuserId(Long.parseLong(authorId));
    
    	if(subscription != null) 
            execution.setVariable("payed", true);
        else {
            execution.setVariable("payed", false);
            
        }
    	
    	//execution.setVariable("authorId", authorId);
	}
}


