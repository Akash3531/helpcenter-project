package com.helpCenter.requestHandlers.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.repository.CategoryRepo;
import com.helpCenter.requestHandlers.service.RequestHandlerService;

@Service
public class RequestHandlerServiceImplementation implements RequestHandlerService{
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Override
	public void handler(Incident incident) {

		Category category = categoryRepo.findByCode(incident.getCategoryCode());
	}

}
