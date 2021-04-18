package com.todolist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;

@Controller
public class TaskController {

	@Autowired TaskRepository tRepository;
	@Autowired UserRepository uRepository;
	
	// Frontpage
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String mainPage() {
		return "index";
	}
	
	// Tasklist page
	@RequestMapping(value="/tasklist", method = RequestMethod.GET)
	public String tasklist(Model model) {
		model.addAttribute("tasks", tRepository.findAll());
		return "tasklist";
	}
	
	// Add a task
	@RequestMapping(value="**/addtask", method = RequestMethod.GET)
	public String addTask(Model model) {
		model.addAttribute("task", new Task());
		return "addtask";
	}
	
	// Save a task on addtask page
	@RequestMapping(value = "/savetask", method = RequestMethod.POST)
	public String saveTask(Task task) {
		task.setUser(uRepository.findByUsername("user"));
		tRepository.save(task);
		return "redirect:tasklist";			
	}
		
	// Login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}	
	
	// Register page
	@RequestMapping(value ="/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	// Register save new user
	@RequestMapping(value ="/registersave", method = RequestMethod.POST)
	public String register(User user) {
		user.setRole("USER");
		user.setPasswordHash("$2y$12$nKwIJxT9VZyzrtsQ.SyAP.LT8XW6SF1cxZfxpJpmiztKM58RH4Raa");
		uRepository.save(user);
		return "redirect:login";
	}
	
	
	
}
