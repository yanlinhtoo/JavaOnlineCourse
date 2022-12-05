package com.hostmdy.ppm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,value = HttpStatus.BAD_REQUEST,reason = "project is not found")
public class ProjectNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 991608761333484200L;
	
	public ProjectNotFoundException(String message) {
		super(message);
	}

}
