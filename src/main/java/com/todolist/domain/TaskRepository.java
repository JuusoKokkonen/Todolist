package com.todolist.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long>{

	List<Task> findByUser(User user);

	Optional<Task> findById(Long taskId);


}
