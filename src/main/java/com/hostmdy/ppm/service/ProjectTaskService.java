package com.hostmdy.ppm.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ppm.domain.ProjectTask;

public interface ProjectTaskService {

	ProjectTask saveOrUpdate(ProjectTask projectTask);
	
	List<ProjectTask> findAll();
	
	Optional<ProjectTask> findByProjectSequence(String projectSequence);
	
	Optional<ProjectTask> findById(Long id);
	
	ProjectTask addProjectToBacklog(String projectIdentifier,ProjectTask projectTask);
}
