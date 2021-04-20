package com.todolist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TasklistRepository extends CrudRepository<Tasklist, Long>{

	Tasklist findByName(String string);

	List<Tasklist> findByUser(User findByUsername);

	Tasklist findByListId(Long listId);


}
