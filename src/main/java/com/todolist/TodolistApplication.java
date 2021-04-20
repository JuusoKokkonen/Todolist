package com.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.todolist.TodolistApplication;
import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;
import com.todolist.domain.Tasklist;
import com.todolist.domain.TasklistRepository;
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;

@SpringBootApplication
public class TodolistApplication {

	private static final Logger log = LoggerFactory.getLogger(TodolistApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TaskRepository tRepository, UserRepository uRepository, TasklistRepository tlRepository) {
		return (args) -> {
			
			uRepository.save(new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
			uRepository.save(new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN"));
			
			tlRepository.save(new Tasklist("testilista", uRepository.findByUsername("user")));
			tlRepository.save(new Tasklist("DEMOLISTA", uRepository.findByUsername("user")));
		
			Task demo1 = new Task("Testi", "ToDo", uRepository.findByUsername("user"), tlRepository.findByName("testilista"));
			Task demo11 = new Task("Testi", "ToDo", uRepository.findByUsername("user"), tlRepository.findByName("testilista"));
			Task demo12 = new Task("Testiiiiii", "ToDo", uRepository.findByUsername("user"), tlRepository.findByName("testilista"));
			Task demo13 = new Task("Testi", "ToDo", uRepository.findByUsername("user"), tlRepository.findByName("testilista"));
			Task demo14 = new Task("Testi", "Done", uRepository.findByUsername("user"), tlRepository.findByName("testilista"));
			Task demo15 = new Task("Testi", "Done", uRepository.findByUsername("user"), tlRepository.findByName("DEMOLISTA"));
			Task demo2 = new Task("Demo", "ToDo", uRepository.findByUsername("user"), tlRepository.findByName("DEMOLISTA"));
			
			tRepository.save(demo1);
			tRepository.save(demo2);
			tRepository.save(demo11);
			tRepository.save(demo12);
			tRepository.save(demo13);
			tRepository.save(demo14);
			tRepository.save(demo15);
			
			

			
			log.info("Fetch all tasks");
			for (Task task : tRepository.findAll()) {
			   log.info(task.toString());
			}
			
			log.info("Fetch all users");
			for (User user : uRepository.findAll()) {
			   log.info(user.toString());
			}
			
		};
	}
	
}