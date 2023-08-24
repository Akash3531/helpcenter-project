package com.helpCenter.Incident.incidenthandler.reposatiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helpCenter.Incident.incidenthandler.Entity.IncidentHandler;

public interface IncidentHandlerReposatiory extends JpaRepository<IncidentHandler, Integer> {

	@Query(value = "select * from public.incident_handler where incident_id=?", nativeQuery = true)
	IncidentHandler getHandler(int id);

}
