package com.hostmdy.ppm.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostmdy.ppm.domain.Backlog;
import com.hostmdy.ppm.domain.Project;
import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.exception.ProjectIdException;
import com.hostmdy.ppm.exception.ProjectNotFoundException;
import com.hostmdy.ppm.repository.BacklogRepository;
import com.hostmdy.ppm.repository.ProjectRepository;
import com.hostmdy.ppm.repository.UserRepository;
import com.hostmdy.ppm.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final BacklogRepository backlogRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, BacklogRepository backlogRepository) {
		super();
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.backlogRepository = backlogRepository;
	}

	@Override
	public Project saveOrUpdate(Project project,String username) {
		// TODO Auto-generated method stub
		String projectIdentifier = project.getProjectIdentifier().toUpperCase();
		User user = userRepository.findByUsername(username).get();
		
		if(project.getId() != null) {
			Optional<Project> existedProjectOpt = projectRepository.findByProjectIdentifier(
					projectIdentifier);
			Project existedproject = existedProjectOpt.get();
			
			if(existedProjectOpt.isPresent() && 
				(!existedproject.getProjectLeader().equals(username))) {
					throw new ProjectNotFoundException("Project not found in your account");	
			}
			
			if(existedProjectOpt.isEmpty()) {
				throw new ProjectNotFoundException("Prject with id="+existedproject.getId()
					+" does not exist.Therefore,you cant update project");
			}
			
			//project - user
			project.setUser(user);
			user.getProjects().add(project);
			
			//project - backlog
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier).get();
			project.setBacklog(backlog);
		    backlog.setProject(project);
			
			project.setProjectLeader(username);
			project.setProjectIdentifier(projectIdentifier);
			
			return projectRepository.save(project);
			
		}else {
			Backlog backlog = new Backlog();
			backlog.setProjectIdentifier(projectIdentifier);
			
			project.setProjectIdentifier(projectIdentifier);
			
			//project - user
			project.setUser(user);
			project.setProjectLeader(username);
			user.getProjects().add(project);
			
			//project - backlog
			project.setBacklog(backlog);
			backlog.setProject(project);
			
			return projectRepository.save(project);
		}
	}

	@Override
	public List<Project> findAll(String username) {
		// TODO Auto-generated method stub
		return projectRepository.findByProjectLeader(username);
	}

	@Override
	public Optional<Project> findByIdentifier(String identifier,String username) {
		// TODO Auto-generated method stub
		Optional<Project> projectOpt = projectRepository.findByProjectIdentifier(identifier.toUpperCase());
		
		if(projectOpt.isEmpty())
			throw new ProjectIdException("Project with id="+identifier+" does not exist");
		
		if(!projectOpt.get().getProjectLeader().equals(username))
			throw new ProjectNotFoundException("Project not found in your account");
		
		return projectOpt;
		
	}

	@Override
	public void flashDelete(String identifier,String username) {
		Optional<Project> projectOpt = projectRepository.findByProjectIdentifier(identifier.toUpperCase());
		Project project = projectOpt.get();
		if(projectOpt.isEmpty())
			throw new ProjectIdException("Project with id="+identifier+" does not exist");
		
		if(!projectOpt.get().getProjectLeader().equals(username))
			throw new ProjectNotFoundException("Project not found in your account");
		
		project.setStatus("deleted");
		project.getBacklog().setStatus("deleted");
		projectRepository.save(project);
		
	}
	
	
	
	

}
