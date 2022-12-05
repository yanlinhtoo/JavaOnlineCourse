package com.hostmdy.ppm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hostmdy.ppm.service.serviceimpl.CustomUserDetailsService;

@Configuration
public class SecurityConfig{
	
	static final String[] PUBLIC_MATCHER = {"/","/favicon.ico","/*/*.png","/*/*.gif","/*/*.svg","/*/*.jpg","/*/*.html","/*/*.js","/*/*.css"};
	static final String SIGNUP_URL = "/api/user/**";
	static final String H2_URL = "/h2-console/**";
	
	private final JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public SecurityConfig(JWTAuthenticationEntryPoint authenticationEntryPoint, CustomUserDetailsService customUserDetailsService, JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers().frameOptions().sameOrigin()
			.and()
			.authorizeHttpRequests()
			.requestMatchers(PUBLIC_MATCHER).permitAll()
			.requestMatchers(SIGNUP_URL).permitAll()
//			.requestMatchers(H2_URL).permitAll()
			.anyRequest().authenticated();
			
	    http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web)-> web.ignoring().requestMatchers(H2_URL);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(customUserDetailsService);
		daoProvider.setPasswordEncoder(passwordEncoder());
		return daoProvider;
	}

}
