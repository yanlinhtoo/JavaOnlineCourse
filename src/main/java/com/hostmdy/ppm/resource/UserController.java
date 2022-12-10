package com.hostmdy.ppm.resource;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.payload.JwtLoginSuccessResponse;
import com.hostmdy.ppm.payload.LoginRequest;
import com.hostmdy.ppm.security.JwtTokenProvider;
import com.hostmdy.ppm.service.MapValidationErrorService;
import com.hostmdy.ppm.service.UserService;
import com.hostmdy.ppm.validator.UserValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final String TOKEN_PREFIX = "Bearer ";
	
	private final UserService userService;
	private final MapValidationErrorService mapErrorService;
	private final UserValidator userValidator;
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	public UserController(UserService userService, MapValidationErrorService mapErrorService, UserValidator userValidator, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.mapErrorService = mapErrorService;
		this.userValidator = userValidator;
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,BindingResult result){
		
		Optional<ResponseEntity<?>> responseErrorObjectOpt = mapErrorService.validate(result);
		
		if(responseErrorObjectOpt.isPresent())
			return responseErrorObjectOpt.get();
		
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = TOKEN_PREFIX+tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JwtLoginSuccessResponse(true,jwt));
		
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user,BindingResult result){
		userValidator.validate(user, result);
		
		Optional<ResponseEntity<?>> responseErrorObjectOpt = mapErrorService.validate(result);
		
		if(responseErrorObjectOpt.isPresent())
			return responseErrorObjectOpt.get();
		
		User createdUser = userService.createUser(user);
		
		return new ResponseEntity<User>(createdUser,HttpStatus.CREATED);
		
	}

}
