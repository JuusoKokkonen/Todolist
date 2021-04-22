package com.todolist;


import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.todolist.domain.Task;
import com.todolist.domain.TaskRepository;
import com.todolist.domain.Tasklist;
import com.todolist.domain.TasklistRepository;
import com.todolist.domain.User;
import com.todolist.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryTest {
	
	@Autowired TaskRepository tRepository;
	@Autowired UserRepository uRepository;
	@Autowired TasklistRepository tlRepository;
	
	// Tests creating fetching and deleting task
	@Test
	public void testTask() {

		User testUser = new User("TestUser", "PasswordHash", "USER");
		Tasklist testTasklist = new Tasklist("TestList", testUser);
		Task testTask = new Task("TestTask", "ToDo", testUser, testTasklist);
		tRepository.save(testTask);
		
		Task task = tRepository.findById(testTask.getId()).get();
		assertThat(task).isNotNull();
		assertThat(task == testTask);
		tRepository.deleteById(task.getId());
		Optional<Task> tasks = tRepository.findById(testTask.getId());
		assertThat(tasks == null); 
			
	}
	
	// Tests creating fetching and deleting tasklist
	@Test
	public void testTasklist() {
		User testUser = new User("TestUser", "PasswordHash", "USER");
		Tasklist testTasklist = new Tasklist("TestList", testUser);
		tlRepository.save(testTasklist);
		
		Tasklist tasklist = tlRepository.findById(testTasklist.getListId()).get();
		assertThat(tasklist).isNotNull();
		assertThat(tasklist == testTasklist);
		tRepository.deleteById(tasklist.getListId());
		Optional<Tasklist> tasklists = tlRepository.findById(testTasklist.getListId());
		assertThat(tasklists == null); 
	}
	
	// Tests creating fetching and deleting user
	@Test
	public void testUser() {
		User testUser = new User("TestUser", "PasswordHash", "USER");
		uRepository.save(testUser);
		
		User user = uRepository.findById(testUser.getUserid()).get();
		assertThat(user).isNotNull();
		assertThat(user == testUser);
		uRepository.deleteById(user.getUserid());
		Optional<User> users = uRepository.findById(testUser.getUserid());
		assertThat(users == null); 
	}
}
