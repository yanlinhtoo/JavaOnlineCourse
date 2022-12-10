package com.hostmdy.ppm.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorService {
	Optional<ResponseEntity<?>> validate(BindingResult result);
}
