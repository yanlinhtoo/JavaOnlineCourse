package com.hostmdy.ppm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,value = HttpStatus.BAD_REQUEST,reason = "ProjectId is invalid")
public class ProjectIdException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2424200819307428498L;

	public ProjectIdException(String message){
		  super(message);
	}
}
