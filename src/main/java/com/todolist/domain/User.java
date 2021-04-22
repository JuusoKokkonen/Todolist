package com.todolist.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false, updatable = false)
	private Long userid;
	
	// unique username
	@NotBlank(message = "name is mandatory")
	@Column(name = "username", nullable = false, unique = true)
	private String username;
			
	@Column(name = "password", nullable = false)
	private String passwordHash;
			
	@Column(name = "role", nullable = false)
	private String role;
	
	@Column
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private List<Tasklist> lists;
	
	@Column
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private List<Task> tasks;	
	
	// Constructors
	public User() {
				
		}
			
	public User(String username, String passwordHash, String role) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
			}
	
	// Getters&Setters
	

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Tasklist> getLists() {
		return lists;
	}
	
	public void setLists(List<Tasklist> lists) {
		this.lists = lists;
	}

	//ToString
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", passwordHash=" + passwordHash + ", role=" + role
				+ "]";
	}
	
	
	

	
	
}
