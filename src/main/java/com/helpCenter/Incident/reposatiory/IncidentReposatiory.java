package com.helpCenter.Incident.reposatiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helpCenter.Incident.entity.Incident;

public interface IncidentReposatiory extends JpaRepository<Incident, Integer>{

	Incident findById(int id);
	
	@Query(value = "Select * from incident where user_id=?", nativeQuery = true)
	public List<Incident> findByIncident(int user_id);
}
