package com.hostmdy.ppm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ppm.domain.User;

public interface UserRepository extends CrudRepository<User,Long>{
	
	Optional<User> findByUsername(String username);

}
