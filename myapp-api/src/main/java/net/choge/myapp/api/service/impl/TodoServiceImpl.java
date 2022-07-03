package net.choge.myapp.api.service.impl;

import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.entity.TodoStatusEntity;
import net.choge.myapp.api.publisher.TodoEventPublisher;
import net.choge.myapp.api.repository.TodoRepository;
import net.choge.myapp.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository repo;

    private TodoEventPublisher publisher;

    public TodoServiceImpl(@Autowired TodoRepository repo,
                           @Autowired TodoEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    public List<TodoItemEntity> loadAllTodosForUser(String userId) {
        return null;
    }

    @Override
    public TodoItemEntity loadSingleTodoItem(String userId, String todoId) {
        TodoItemEntity todo = repo.retrieveTodoItem(userId, todoId);

        publisher.publishTodoEvent(todo);

        return todo;
    }

    @Override
    public boolean updateTodoStatus(String userId, String todoId, TodoStatusEntity status) {
        return false;
    }
}
