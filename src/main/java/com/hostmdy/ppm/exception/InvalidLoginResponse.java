package com.hostmdy.ppm.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
	
	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		// TODO Auto-generated constructor stub
		this.username = "Invalid Username";
		this.password = "Invalid Password";
	}
}
