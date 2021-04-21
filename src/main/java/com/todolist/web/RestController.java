package com.todolist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;

@Controller
public class RestController {

	@Autowired
	private TaskRepository tRepository;
	
	// REST users all tasks
	@RequestMapping(value="/tasks", method = RequestMethod.GET)
	public @ResponseBody List<Task> tasksRest() {
		return (List<Task>) tRepository.findAll();
	}
	
	
}
