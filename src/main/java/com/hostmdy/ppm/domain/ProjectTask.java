package com.hostmdy.ppm.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProjectTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "project sequence is required")
	@Column(updatable = false,unique = true)
	private String projectSequence;
	
	@NotBlank(message = "summary is required")
	private String summary;
	
	private String acceptanceCriteria;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.TODO;
	
	private Integer priority = 0;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id"/*,nullable = false*/,
	updatable = false)
	@JsonIgnore
	private Backlog backlog;
	
	@PrePersist
	void OnCreate() {
		this.createdAt = LocalDate.now();
	}
	
	@PreUpdate
	void OnUpdate() {
		this.updatedAt = LocalDate.now();
	}
	

}
