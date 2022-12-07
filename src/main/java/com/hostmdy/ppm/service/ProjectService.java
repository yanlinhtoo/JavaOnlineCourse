package com.hostmdy.ppm.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ppm.domain.Project;

public interface ProjectService {
	
	Project saveOrUpdate(Project project,String username);
	
	List<Project> findAll(String username);
	
	Optional<Project> findByIdentifier(String identifier,String username);
	
	void flashDelete(String identifier,String username);

}
