package com.todolist.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task {

	
	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String description;
	private String status;
	private String deadline;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listId")
	private Tasklist tasklist;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;
	
	
	// Constructors
	public Task(String description, String status, User user, Tasklist tasklist) {
		super();
		this.description = description;
		this.status = status;
		this.tasklist = tasklist;
		this.user = user;
	}
	
	public Task() {
		super();
		this.description = null;
		this.status = null;
		this.deadline = null;
		this.tasklist = null;
		this.user = null;
	}

	
	// Getters&Setters	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDeadline() {
		return deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Tasklist getTasklist() {
		return tasklist;
	}

	public void setTasklist(Tasklist tasklist) {
		this.tasklist = tasklist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


		
	
}
