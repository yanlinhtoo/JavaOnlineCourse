package com.hostmdy.ppm.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ppm.domain.Project;

public interface ProjectService {
	
	Project saveOrUpdate(Project project);
	
	List<Project> findAll();
	
	Optional<Project>  findById(Long id);

}
