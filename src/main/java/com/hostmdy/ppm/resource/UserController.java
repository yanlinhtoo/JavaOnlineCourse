package com.hostmdy.ppm.resource;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.service.MapValidationErrorService;
import com.hostmdy.ppm.service.UserService;
import com.hostmdy.ppm.validator.UserValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	private final MapValidationErrorService mapErrorService;
	private final UserValidator userValidator;

	public UserController(UserService userService, MapValidationErrorService mapErrorService, UserValidator userValidator) {
		super();
		this.userService = userService;
		this.mapErrorService = mapErrorService;
		this.userValidator = userValidator;
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<User> userOptional = userService.getUserById(id);
		
		if(userOptional.isEmpty())
			return new ResponseEntity<String>("User with id="+id+" is not found",HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<User>(userOptional.get(),HttpStatus.OK);
		
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable String username){
		Optional<User> userOptional = userService.findByUsername(username);
		
		if(userOptional.isEmpty())
			return new ResponseEntity<String>("User with username="+username+"is not found",HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<User>(userOptional.get(),HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user,BindingResult result){
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorResponse = mapErrorService.validate(result);
		
		if(errorResponse != null)
			return errorResponse;
		
		User createdUser = userService.createUser(user);
		
		return new ResponseEntity<User>(createdUser,HttpStatus.CREATED);
		
	}

}
