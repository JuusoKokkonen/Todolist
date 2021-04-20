package com.todolist.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;
import com.todolist.domain.Tasklist;
import com.todolist.domain.TasklistRepository;
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;

@Controller
public class TaskController {
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
		
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired TaskRepository tRepository;
	@Autowired UserRepository uRepository;
	@Autowired TasklistRepository tlRepository;
	
	// Frontpage
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String mainPage() {
		return "index";
	}
		
	// Tasklist page
	@RequestMapping(value="/tasklist", method = RequestMethod.GET)
	public String tasklist(Model model) {
		String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("tasks", tRepository.findByUser(uRepository.findByUsername(currUser)));
		model.addAttribute("lists", tlRepository.findByUser(uRepository.findByUsername(currUser)));
		return "tasklist";
	}
	
	// Add a task
	@RequestMapping(value="**/addtask/{id}", method = RequestMethod.GET)
	public String addTask(@PathVariable("id") Long listId, Model model) {
		String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Task newtask = new Task(null, "ToDo", uRepository.findByUsername(currUser), tlRepository.findByListId(listId));
		newtask.setTasklist(tlRepository.findById(listId).get());
		System.out.println(newtask);
		model.addAttribute("task", newtask);
		return "addtask";
	}
	
	// Save a task on addtask page
	@RequestMapping(value = "/savetask", method = RequestMethod.POST)
	public String saveTask(Task task) {
		System.out.println(task);
		String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(currUser);
		task.setUser(uRepository.findByUsername(currUser));
		task.setStatus("ToDo");
		tRepository.save(task);
		return "redirect:tasklist";			
	}
	
	// Delete task
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTask(@PathVariable("id") Long taskId, Model model) {
		tRepository.deleteById(taskId);
		return "redirect:../tasklist";
	}
	
	// Change status
	@RequestMapping(value = "/changestatus/{id}", method = RequestMethod.GET)
	public String changeStatus(@PathVariable("id") Long taskId, Model model) {
		Task thisTask = tRepository.findById(taskId).get();
		System.out.println(thisTask);
		if (thisTask.getStatus() == "ToDo") {
			thisTask.setStatus("InProgress");
			tRepository.save(thisTask);
			return "redirect:../tasklist";
		} else if (thisTask.getStatus() == "InProgress") {
			thisTask.setStatus("Done");
			tRepository.save(thisTask);
			return "redirect:../tasklist";
		} else {
			thisTask.setStatus("ToDo");
			tRepository.save(thisTask);
			return "redirect:../tasklist";
		}
	}
	
	// Edit task
	@RequestMapping(value = "/edittask/{id}", method = RequestMethod.GET)
	public String editTask(@PathVariable("id") Long taskId, Model model) {
		model.addAttribute("task", tRepository.findById(taskId));
		return "edittask";
	}
	
	// Save edited task
	@RequestMapping(value = "/editsave/{id}", method = RequestMethod.POST)
	public String saveEdited(@PathVariable("id") Long taskId, Task task) {
		tRepository.save(task);
		return "redirect:../tasklist";
	}
	
	// Add a new tasklist
	@RequestMapping(value = "/newlist", method = RequestMethod.GET)
	public String newList(Model model) {
		model.addAttribute("tasklist", new Tasklist());
		return "addlist";
	}
	
	// Save the new tasklist
	@RequestMapping(value = "/savelist", method = RequestMethod.POST)
	public String saveList(Tasklist tasklist) {
		String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
		tasklist.setUser(uRepository.findByUsername(currUser));
		tlRepository.save(tasklist);
		return "redirect:tasklist";			
	}
	
	// Delete tasklist
	@RequestMapping(value = "/deletelist/{id}", method = RequestMethod.GET)
	public String deleteList(@PathVariable("id") Long listId, Model model) {
		tlRepository.deleteById(listId);
		return "redirect:../tasklist";
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
	
	// Save new user
	@RequestMapping(value ="/registersave", method = RequestMethod.POST)
	public String register(User user) {
		user.setRole("USER");
		user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
		uRepository.save(user);
		return "redirect:login";
	}
	
	
	
}
