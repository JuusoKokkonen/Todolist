package com.todolist.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tasklist {
	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "listId", nullable = false, updatable = false)
	private Long listId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tasklist", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Task> tasks;

	
	// Constructors
	public Tasklist(String name, User user) {
		super();
		this.name = name;
		this.user = user;
	}
	
	public Tasklist() {
		super();
		this.name = null;
		this.user = null;
	}
	
	
	// Getters & Setters
	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	

	// toString
	@Override
	public String toString() {
		return "Tasklist [listId=" + listId + ", name=" + name + ", user=" + user + "]";
	}
	
	
}
