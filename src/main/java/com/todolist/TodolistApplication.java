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
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;

@SpringBootApplication
public class TodolistApplication {

	private static final Logger log = LoggerFactory.getLogger(TodolistApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TaskRepository tRepository, UserRepository uRepository) {
		return (args) -> {
			
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			uRepository.save(user1);
			uRepository.save(user2);
		
			Task demo1 = new Task("Testi", user1);
			Task demo2 = new Task("Demo", user2);
			//Book demo2 = new Book("Demo", "Demo-author", "2010", "ISBN", (long) 19, cRepository.findByName("Fantasy").get(0));
			//Book demo3 = new Book(":)", "Testiauthor", "2021", "testi", (long) 20, cRepository.findByName("Science").get(0));
			
			tRepository.save(demo1);
			tRepository.save(demo2);
			//bRepository.save(demo3);
			

			
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