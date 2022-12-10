package com.hostmdy.ppm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3823234983016085376L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message = "username must be email")
	@NotBlank(message = "username is required")
	@Column(unique = true)
	private String username;
	
	@NotBlank(message = "fullname is required")
	private String fullname;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@Transient
	private String confirmPassword;
	
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER,
			cascade = CascadeType.REFRESH,orphanRemoval = true)
	private List<Project> projects = new ArrayList<>();
	
	public User(@Email(message = "username must be email") @NotBlank(message = "username is required") String username,
			@NotBlank(message = "fullname is required") String fullname,
			@NotBlank(message = "password is required") String password) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.password = password;
	}
	
	@PrePersist
	void OnCreate() {
		this.createdAt = LocalDate.now();
	}
	
	@PreUpdate
	void OnUpdate() {
		this.updatedAt = LocalDate.now();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



}
