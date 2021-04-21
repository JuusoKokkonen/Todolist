package com.todolist.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return "tasklist";
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
		String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
		task.setUser(uRepository.findByUsername(currUser));
		task.setStatus("ToDo");
		System.out.println(task.getDeadline());
		if (task.getDeadline() == "") {
			task.setDeadline(null);
		} else {
			String str = task.getDeadline();
			String deadline = str.replace("T", " ");
			task.setDeadline(deadline);
		}
		
		tRepository.save(task);
		return "redirect:tasklist";			
	}
	
	// Delete task
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTask(@PathVariable("id") Long taskId, Model model) {
		// Validate that current user deletes only his own tasks
		User currUser = uRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = tRepository.findById(taskId).get();
		if (currUser.getUserid() == task.getUser().getUserid()) {
			tRepository.deleteById(taskId);
			return "redirect:../tasklist";
		} else {
			return "authorityerror";
		}
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
		// Validate that current user edits only his own tasks
		User currUser = uRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = tRepository.findById(taskId).get();
		if (currUser.getUserid() == task.getUser().getUserid()) {
			model.addAttribute("task", task);
			return "edittask";
		} else {
			return "authorityerror";
		}
	}
	
	// Save edited task
	@RequestMapping(value = "/editsave/{id}", method = RequestMethod.POST)
	public String saveEdited(@PathVariable("id") Long taskId, Task task) {
		if (task.getDeadline() == "") {
			task.setDeadline(null);
		} else {
			String str = task.getDeadline();
			String deadline = str.replace("T", " ");
			task.setDeadline(deadline);
		}
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
		// Validate that current user deletes his own tasklist
		User currUser = uRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tasklist tasklist = tlRepository.findByListId(listId);
		if (currUser.getUserid() == tasklist.getUser().getUserid()) {
			tlRepository.deleteById(listId);
			return "redirect:../tasklist";
		} else {
			return "authorityerror";
		}
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
		if (uRepository.findByUsername(user.getUsername()) == null) {
			uRepository.save(user);
			return "redirect:login?success";
		} else {
			return "redirect:register?error";	
		}
	}
	
	// For demo purposes (adds 3 tasklists and fills them with a few tasks)
	@RequestMapping(value ="/demo", method = RequestMethod.GET)
	public String demo() {
		User currUser = uRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Tasklist tasklist1 = tlRepository.save(new Tasklist("Sprint 1", currUser));
		Tasklist tasklist2 = tlRepository.save(new Tasklist("Sprint 2", currUser));
		Tasklist tasklist3 = tlRepository.save(new Tasklist("Sprint 3", currUser));
		
		tRepository.save(new Task("Make Task class", "Done", currUser, tasklist1));
		tRepository.save(new Task("Make User class", "Done", currUser, tasklist1));
		tRepository.save(new Task("Create tasklist.html", "Done", currUser, tasklist1));
		tRepository.save(new Task("HTML form bugfix", "InProgress", currUser, tasklist1));
		tRepository.save(new Task("Create controller", "Done", currUser, tasklist1));
		tRepository.save(new Task("Create API", "Done", currUser, tasklist2));
		tRepository.save(new Task("Api tests", "InProgress", currUser, tasklist2));
		tRepository.save(new Task("Demo to customer", "Done", currUser, tasklist2));
		tRepository.save(new Task("Make tests", "ToDo", currUser, tasklist3));
		tRepository.save(new Task("Make validation", "ToDo", currUser, tasklist3));
		tRepository.save(new Task("Fix user role bug", "InProgress", currUser, tasklist3));
		tRepository.save(new Task("Smoke testing", "ToDo", currUser, tasklist3));
		tRepository.save(new Task("Customer demo", "ToDo", currUser, tasklist3));
		return "redirect:tasklist";
	}
	
	
}
