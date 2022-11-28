package com.hostmdy.ppm.service;

import java.util.Optional;

import com.hostmdy.ppm.domain.Backlog;

public interface BacklogService {
	
	Optional<Backlog> findByProjectIdentifier(String projectIdentifier);
	
}
