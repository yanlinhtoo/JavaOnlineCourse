package com.hostmdy.ppm;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hostmdy.ppm.domain.Backlog;
import com.hostmdy.ppm.domain.Project;
import com.hostmdy.ppm.domain.User;
import com.hostmdy.ppm.service.ProjectService;
import com.hostmdy.ppm.service.UserService;

@SpringBootApplication
public class PpmtoolApplication implements CommandLineRunner{

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(PpmtoolApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		Project project1 = new Project("OnlineShop","oshop","This is onlineshop",LocalDate.now(),LocalDate.of(2023,Month.FEBRUARY,24));
//		
//		User user1 = new User("min@gmail.com","MinThuKyaw","1234");
//		
//		project1.setUser(user1);
//		user1.getProjects().add(project1);
//		
//		userService.createUser(user1);
//		projectService.saveOrUpdate(project1);
		
		
	}

}
