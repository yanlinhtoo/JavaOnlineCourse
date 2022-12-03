package com.hostmdy.ppm.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hostmdy.ppm.exception.InvalidLoginResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
		String jsonInvalidResponse = new Gson().toJson(invalidLoginResponse);
		
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().print(jsonInvalidResponse);
	}

}
