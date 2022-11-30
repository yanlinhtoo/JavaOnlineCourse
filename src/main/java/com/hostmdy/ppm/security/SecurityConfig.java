package com.hostmdy.ppm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
	
	private final JWTAuthenticationEntryPoint unauthorizedHandler;
	
	public SecurityConfig(JWTAuthenticationEntryPoint unauthorizedHandler) {
		super();
		this.unauthorizedHandler = unauthorizedHandler;
	}

	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .headers().frameOptions().sameOrigin()
	        .and()
	        .authorizeHttpRequests().requestMatchers("/api/**").permitAll()
	        .anyRequest().authenticated();
	    
	    return http.build();
	  }

}
