package com.hostmdy.ppm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ppm.domain.Project;

public interface ProjectRepository extends CrudRepository<Project,Long>{

	Optional<Project> findByProjectIdentifier(String projectIdentifier);
	
	List<Project> findByProjectLeader(String projectLeader);
}
