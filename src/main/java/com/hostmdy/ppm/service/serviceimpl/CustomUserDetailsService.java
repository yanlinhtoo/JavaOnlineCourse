package com.hostmdy.ppm.service.serviceimpl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepository.findByUsername(username);
		
		if(userOptional.isEmpty())
			throw new UsernameNotFoundException("User with name="+username+" is not found");
		
		return userOptional.get();
	}
	
	@Transactional
	public User loadUserById(Long id) {
		Optional<User> userOptional = userRepository.getUserById(id);
		
		if(userOptional.isEmpty())
			throw new UsernameNotFoundException("User with id="+id+" is not found");
		
		return userOptional.get();
	} 

}
