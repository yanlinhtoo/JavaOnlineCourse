package com.hostmdy.ppm.resource;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ppm.domain.Project;
import com.hostmdy.ppm.service.MapValidationErrorService;
import com.hostmdy.ppm.service.ProjectService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	private final ProjectService projectService;
	private final MapValidationErrorService mapErrorService;

	@Autowired
	public ProjectController(ProjectService projectService, MapValidationErrorService mapErrorService) {
		super();
		this.projectService = projectService;
		this.mapErrorService = mapErrorService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project,BindingResult result,Principal principal){
		ResponseEntity<?> responseErrorObject = mapErrorService.validate(result);
		
		if(responseErrorObject != null)
			return responseErrorObject;
			
		Project createdProject = projectService.saveOrUpdate(project,principal.getName());
		return new ResponseEntity<Project>(createdProject, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/all")
	public List<Project> findAll(Principal principal) {
		return projectService.findAll(principal.getName());
	}
	
	@GetMapping("/identifier/{identifier}")
	public ResponseEntity<?> findByIdentifier(@PathVariable String identifier,Principal principal){
		Optional<Project> projectOptional = projectService.findByIdentifier(identifier,principal.getName());
		
		if(projectOptional.isEmpty())
			return new ResponseEntity<String>("Project with identifier="+identifier+" is not found", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Project>(projectOptional.get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/identifier/{identifier}")
	public ResponseEntity<String> deleteById(@PathVariable String identifier,Principal principal){
		
		projectService.flashDelete(identifier,principal.getName());
		
		return new ResponseEntity<String>("Deleted id="+identifier,HttpStatus.OK);
		
	}
	
	

}
