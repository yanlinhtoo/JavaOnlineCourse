package com.hostmdy.ppm.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.ppm.domain.Backlog;
import com.hostmdy.ppm.domain.ProjectTask;
import com.hostmdy.ppm.exception.ProjectIdException;
import com.hostmdy.ppm.exception.ProjectNotFoundException;
import com.hostmdy.ppm.repository.BacklogRepository;
import com.hostmdy.ppm.repository.ProjectRepository;
import com.hostmdy.ppm.repository.ProjectTaskRepository;
import com.hostmdy.ppm.service.ProjectService;
import com.hostmdy.ppm.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService{

	private final ProjectTaskRepository projectTaskRepository;
	private final BacklogRepository backlogRepository;
	
	public ProjectTaskServiceImpl(ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository, ProjectService projectService, BacklogRepository backlogRepository) {
		super();
		this.projectTaskRepository = projectTaskRepository;
		this.backlogRepository = backlogRepository;
	}
	
	private Backlog checkBacklog(String identifier,String username) {
		
		Optional<Backlog> backlogOpt = backlogRepository.findByProjectIdentifier(identifier);

		if (backlogOpt.isEmpty())
			throw new ProjectIdException("backlog with id=" + identifier + " does not exist");

		Backlog backlog = backlogOpt.get();
		
		if (!backlog.getProject().getProjectLeader().equals(username))
			throw new ProjectNotFoundException("backlog is not found in your acount");
		return backlog;
	}

	@Override
	public ProjectTask createProjectTask(String identifier, ProjectTask projectTask, String username) {
		// TODO Auto-generated method stub
		
		Backlog backlog = checkBacklog(identifier, username);
		
		Integer pTSequence =  backlog.getPTSequence();
		
		pTSequence++;
		backlog.setPTSequence(pTSequence);
		
		projectTask.setProjectSequence(identifier+"-"+pTSequence);
		projectTask.setProjectIdentifier(projectTask.getProjectIdentifier().toUpperCase());
			
		
		if(projectTask.getPriority()==0)
			projectTask.setPriority(5);
		
		projectTask.setBacklog(backlog);
		backlog.getProjectTasks().add(projectTask);
		
		return projectTaskRepository.save(projectTask);
	}

	@Override
	public Optional<ProjectTask> findByProjectSequence(String identifier,String projectSequence, String username) {
		checkBacklog(identifier, username);
		return projectTaskRepository.findByProjectSequence(projectSequence);
	}

	@Override
	public ProjectTask updateProjectTask(String identifier,
			ProjectTask projectTask, String projectSequence, String username) {

		Backlog backlog = checkBacklog(identifier, username);
		
		Optional<ProjectTask> pTOptional = projectTaskRepository.findByProjectSequence(projectSequence);
		
		if(pTOptional.isEmpty())
			throw new ProjectIdException("projectTask with id="+projectSequence+" does not exist");
		
		//projectTask-backlog
		projectTask.setBacklog(backlog);
		backlog.getProjectTasks().add(projectTask);
		
		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deleteProjectTask(String identifier, String projectSequence, String username) {
		// TODO Auto-generated method stub
		checkBacklog(identifier, username);
		
		Optional<ProjectTask> pTOptional = projectTaskRepository.findByProjectSequence(projectSequence);
		
		if(pTOptional.isEmpty())
			throw new ProjectIdException("projectTask with id="+projectSequence+" does not exist");
		
		projectTaskRepository.deleteById(pTOptional.get().getId());
		
		
	}

	@Override
	public List<ProjectTask> findAll(String identifier, String username) {
		// TODO Auto-generated method stub
		checkBacklog(identifier, username);
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(identifier);
	}

}
