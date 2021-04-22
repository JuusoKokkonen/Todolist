package com.todolist;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;
import com.todolist.domain.Tasklist;
import com.todolist.domain.TasklistRepository;
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;
import com.todolist.web.TaskController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodolistApplicationTests {

	@Autowired private TaskController controller;
	@Autowired private TaskRepository tRepository;
	@Autowired private UserRepository uRepository;
	@Autowired private TasklistRepository tlRepository;

	
	// Test that controller and repositories load
	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
		assertThat(tRepository).isNotNull();
		assertThat(uRepository).isNotNull();
		assertThat(tlRepository).isNotNull();
	}
	
}


