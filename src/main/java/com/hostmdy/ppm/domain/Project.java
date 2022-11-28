package com.hostmdy.ppm.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "ProjectName should not be blank")
	private String projectName;
	
	@NotBlank(message = "ProjectIdentifier should not be blank")
	@Size(min = 4,max = 8,message = "4 to 8 charactor should be included")
	@Column(updatable = false,unique = true)
	private String projectIdentifier;
	
	@NotBlank(message = "Description should not be blank")
	private String description;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@OneToOne(mappedBy = "project",cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	@JsonIgnore
	private Backlog backlog;
	
	public Project(@NotBlank(message = "ProjectName should not be blank") String projectName,
			@NotBlank(message = "ProjectIdentifier should not be blank") @Size(min = 4, max = 8, message = "4 to 8 charactor should be included") String projectIdentifier,
			@NotBlank(message = "Description should not be blank") String description, LocalDate startDate,
			LocalDate endDate) {
		super();
		this.projectName = projectName;
		this.projectIdentifier = projectIdentifier;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	@PrePersist
	void OnCreate() {
		this.createdAt = LocalDate.now();
	}
	
	@PreUpdate
	void OnUpdate() {
		this.updatedAt = LocalDate.now();
	}
	
	
	

}
