package com.hostmdy.ppm.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ppm.domain.User;

public interface UserService {
	User saveOrUpdateUser(User user);
	List<User> findAll();
	Optional<User> findById(Long id);
	Optional<User> findByUsername(String username);
}
