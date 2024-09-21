package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TodoService {

	@Autowired
	private TodoRepository repository;

	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		repository.save(entity);
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}

	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);

		repository.save(entity);
		log.info("Entity Id : {} id saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}

	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");
		}

		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}

	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}

	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);

		final Optional<TodoEntity> original = repository.findById(entity.getId());

		//반환된 엔티티가 존재하면 값을 새 엔티티로 덮어씌운다.
		original.ifPresent(todo ->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());

			repository.save(todo);

		});

		return retrieve(entity.getUserId());
	}

	public List<TodoEntity> delete(final  TodoEntity entity) {
		validate(entity);

		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("error delete entity" + entity.getId(),e);

			throw new RuntimeException("error deleting entity" + entity.getId());
		}

		return retrieve(entity.getUserId());
	}



}
