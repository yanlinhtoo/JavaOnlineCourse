package com.hostmdy.ppm.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.exception.UsernameAlreadyExistException;
import com.hostmdy.ppm.repository.UserRepository;
import com.hostmdy.ppm.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User saveOrUpdateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User createUser(User user) throws UsernameAlreadyExistException{
		// TODO Auto-generated method stub
		Optional<User> userOptional = findByUsername(user.getUsername());
		
		if(userOptional.isPresent()) {
			throw new UsernameAlreadyExistException("Username is already exist.");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return saveOrUpdateUser(user);
	}

	@Override
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getUserById(id);
	}

}
