package com.hostmdy.ppm.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Backlog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer pTSequence;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id"/*,nullable = false*/)
	private Project project;
	
	@OneToMany(mappedBy = "backlog",cascade = CascadeType.REFRESH,
			fetch = FetchType.EAGER,orphanRemoval = true)
	@JsonIgnore
	private List<ProjectTask> projectTasks = new ArrayList<>();
	

}
