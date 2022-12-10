package com.hostmdy.ppm.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hostmdy.ppm.service.MapValidationErrorService;

@Service
public class MapValidationErrorServiceImpl implements MapValidationErrorService{

	@Override
	public Optional<ResponseEntity<?>> validate(BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasErrors()) {
			Map<String,String> errorMap = new HashMap<>();
			List<FieldError> errors= result.getFieldErrors();
			
			for(final FieldError error:errors)
				errorMap.put(error.getField(),error.getDefaultMessage());
			
			Optional<ResponseEntity<?>> responseOpt = Optional.of(new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST));
			
			return responseOpt;
		}
		
		return Optional.empty();
	}

}
