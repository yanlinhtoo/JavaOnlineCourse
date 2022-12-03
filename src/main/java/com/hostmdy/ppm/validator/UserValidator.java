package com.hostmdy.ppm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hostmdy.ppm.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;
		
		if(user.getPassword().length() < 8)
			errors.rejectValue("password","Length","Password must be at least 8 charactors");
		
		if(!user.getPassword().equals(user.getConfirmPassword()))
			errors.rejectValue("confirmPassword","Match","Confirm password must be match with password");
	}

}
