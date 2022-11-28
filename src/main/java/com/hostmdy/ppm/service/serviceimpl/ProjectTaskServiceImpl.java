package com.hostmdy.ppm.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.ppm.domain.Backlog;
import com.hostmdy.ppm.domain.ProjectTask;
import com.hostmdy.ppm.domain.Status;
import com.hostmdy.ppm.repository.ProjectRepository;
import com.hostmdy.ppm.repository.ProjectTaskRepository;
import com.hostmdy.ppm.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService{

	private final ProjectTaskRepository projectTaskRepository;
	private final ProjectRepository projectRepository;
	
	public ProjectTaskServiceImpl(ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
		super();
		this.projectTaskRepository = projectTaskRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	public ProjectTask saveOrUpdate(ProjectTask projectTask) {
		// TODO Auto-generated method stub
		return projectTaskRepository.save(projectTask);
	}

	@Override
	public List<ProjectTask> findAll() {
		// TODO Auto-generated method stub
		return (List<ProjectTask>) projectTaskRepository.findAll();
	}

	@Override
	public Optional<ProjectTask> findByProjectSequence(String projectSequence) {
		// TODO Auto-generated method stub
		return projectTaskRepository.findByProjectSequence(projectSequence);
	}

	@Override
	public Optional<ProjectTask> findById(Long id) {
		// TODO Auto-generated method stub
		return projectTaskRepository.findById(id);
	}

	@Override
	public ProjectTask addProjectToBacklog(String projectIdentifier, ProjectTask projectTask) {
		// TODO Auto-generated method stub
		Backlog backlog = projectRepository.findByProjectIdentifier(projectIdentifier).get().getBacklog();
		
		Integer pTSequence =  backlog.getPTSequence();
		
		if(pTSequence == null)
			pTSequence = 0;
		
		pTSequence++;
		backlog.setPTSequence(pTSequence);
		
		projectTask.setProjectSequence(projectIdentifier+"-"+pTSequence);
			
		
		if(projectTask.getPriority()==null || projectTask.getPriority()==0)
			projectTask.setPriority(5);
		
		if(projectTask.getStatus()==null)
			projectTask.setStatus(Status.TODO);
		
		projectTask.setBacklog(backlog);
		backlog.getProjectTasks().add(projectTask);
		
		return projectTaskRepository.save(projectTask);
	}

}
