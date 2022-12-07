package com.hostmdy.ppm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ppm.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long>{
	
	Optional<ProjectTask> findByProjectSequence(String projectSequence);
	
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
}
