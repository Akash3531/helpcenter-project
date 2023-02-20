package com.helpCenter.Incident.reposatiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpCenter.Incident.entity.ImageCreation;

public interface ImageReposatiory extends JpaRepository<ImageCreation, Integer>{

}
