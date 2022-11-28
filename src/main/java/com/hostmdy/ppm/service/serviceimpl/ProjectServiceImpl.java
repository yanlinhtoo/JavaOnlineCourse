package com.hostmdy.ppm.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostmdy.ppm.domain.Backlog;
import com.hostmdy.ppm.domain.Project;
import com.hostmdy.ppm.repository.ProjectRepository;
import com.hostmdy.ppm.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		super();
		this.projectRepository = projectRepository;
	}

	@Override
	public Project saveOrUpdate(Project project) {
		// TODO Auto-generated method stub
		Backlog backlog = new Backlog();
		backlog.setProjectIdentifier(project.getProjectIdentifier());
		project.setBacklog(backlog);
	    backlog.setProject(project);
		
		return projectRepository.save(project);
	}

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		return (List<Project>) projectRepository.findAll();
	}

	@Override
	public Optional<Project> findById(Long id) {
		// TODO Auto-generated method stub
		return projectRepository.findById(id);
	}

	@Override
	public Optional<Project> findByIdentifier(String identifier) {
		// TODO Auto-generated method stub
		return projectRepository.findByProjectIdentifier(identifier);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		projectRepository.deleteById(id);
		
	}
	
	
	
	

}
