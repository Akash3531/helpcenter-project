package com.helpCenter.Incident.reposatiory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helpCenter.Incident.entity.Incident;

public interface IncidentReposatiory extends JpaRepository<Incident, Integer> {

	Incident findById(int id);

	@Query(value = "Select * from incident where user_id=?", nativeQuery = true)
	public List<Incident> findIncidentByUserId(int user_id, Pageable p);

	@Query(value = "Select * from incident where category_code=?", nativeQuery = true)
	public List<Incident> findIncidentByCode(String categoryCode, Pageable p);

	@Query(value = "Select * from \"incident\" where category_code=? and status='ToDo'", nativeQuery = true)
	public List<Incident> findIncidentByCategoryCodeAndStatus(String categoryCode);
}
