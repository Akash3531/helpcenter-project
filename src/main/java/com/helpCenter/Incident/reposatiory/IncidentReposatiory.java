package com.helpCenter.Incident.reposatiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpCenter.Incident.entity.Incident;

public interface IncidentReposatiory extends JpaRepository<Incident, Integer>{

	Incident findById(int id);
}
