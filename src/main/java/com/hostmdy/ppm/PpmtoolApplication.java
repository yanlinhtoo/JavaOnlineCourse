package com.hostmdy.ppm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.service.UserService;

@SpringBootApplication
public class PpmtoolApplication implements CommandLineRunner{
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(PpmtoolApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		List<User> userList = userService.findAll();

		if (userList.isEmpty()) {
			User user1 = new User("min@gmail.com", "MinThuKyaw", "admin@1234");

			userService.createUser(user1);
		}
	}

}
