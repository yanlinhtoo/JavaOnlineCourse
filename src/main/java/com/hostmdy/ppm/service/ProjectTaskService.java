package com.hostmdy.ppm.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ppm.domain.ProjectTask;

public interface ProjectTaskService {
	
	Optional<ProjectTask> findByProjectSequence(String identifier,String projectSequence,String username);
	
	List<ProjectTask> findAll(String identifier,String username);
	
	ProjectTask createProjectTask(String projectIdentifier,ProjectTask projectTask,String username);
	
	ProjectTask updateProjectTask(String identifier,ProjectTask projectTask,String projectSequence, String username);
	
	void deleteProjectTask(String identifier,String projectSequence,String username);
}
