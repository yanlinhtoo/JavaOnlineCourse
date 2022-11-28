package com.hostmdy.ppm.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ppm.domain.ProjectTask;
import com.hostmdy.ppm.service.MapValidationErrorService;
import com.hostmdy.ppm.service.ProjectTaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/task")
public class ProjectTaskController {
	private final ProjectTaskService projectTaskService;
	private final MapValidationErrorService mapErrorService;

	public ProjectTaskController(ProjectTaskService projectTaskService, MapValidationErrorService mapErrorService) {
		super();
		this.projectTaskService = projectTaskService;
		this.mapErrorService = mapErrorService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createProjectTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result){
		ResponseEntity<?> errorResponse = mapErrorService.validate(result);
		
		if(errorResponse != null)
			return errorResponse;
		
		ProjectTask createdProjectTask = projectTaskService.saveOrUpdate(projectTask);
	    return new ResponseEntity<ProjectTask>(createdProjectTask,HttpStatus.CREATED);
	}
	
	@PostMapping("/assign/identifier/{identifier}")
	public ResponseEntity<?> addProjectTaskToBacklog(@PathVariable String identifier,
			@Valid @RequestBody ProjectTask projectTask,BindingResult result){
		ResponseEntity<?> errorResponse = mapErrorService.validate(result);
		
		if(errorResponse != null)
			return errorResponse;
		
		ProjectTask assignedProjectTask = projectTaskService.addProjectToBacklog(identifier, projectTask);
		
		return new ResponseEntity<ProjectTask>(assignedProjectTask,HttpStatus.OK);
		
	}
	
	
}
