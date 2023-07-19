package com.helpCenter.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;

@Aspect
@Component
public class IncidentHandlerAspect {
   
   
   @AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "incident")
  public void  AssignIncidentHandlerWithIncident(Incident incident) {
	    
	   
	   
  }
   
}
