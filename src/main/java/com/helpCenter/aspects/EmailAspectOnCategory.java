package com.helpCenter.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpCenter.category.entity.Category;
import com.helpCenter.notificationsEmails.informationProviderServiceImpl.InformationProviderForEmailServiceImpl;

@Aspect
@Component
public class EmailAspectOnCategory {
	@Autowired
	InformationProviderForEmailServiceImpl providerForEmailServiceImpl;
	
	//Call information provider after category creation
	@AfterReturning(pointcut = "execution(* com.helpCenter.category.serviceImpl.CategoryServiceImpl.createCategory(..))", returning = "Category")
	public void sendMailOnCategoryCreation(Category Category )
	{
		providerForEmailServiceImpl.getCategoryCreateDetails(Category);
	}
	
	//Call information provider after category updation
	@AfterReturning(pointcut = "execution(* com.helpCenter.category.serviceImpl.CategoryServiceImpl.updateFields(..))", returning = "Category")
	public void sendMailOnCategoryUpdatetion(Category Category)
	{
		providerForEmailServiceImpl.getDetailsOnCategoryUpdation(Category);
	}
}
