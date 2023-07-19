package com.helpCenter.Incident.reposatiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpCenter.Incident.entity.IncidentHandler;

public interface IncidentHandlerReposatiory extends JpaRepository<IncidentHandler, Integer> {

	
}
