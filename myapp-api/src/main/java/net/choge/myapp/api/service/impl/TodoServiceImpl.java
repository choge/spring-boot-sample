package net.choge.myapp.api.service.impl;

import net.choge.myapp.api.entity.TodoItem;
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
    public List<TodoItem> loadAllTodosForUser(String userId) {
        return null;
    }

    @Override
    public TodoItem loadSingleTodoItem(String userId, String todoId) {
        return repo.retrieveTodoItem(userId, todoId);
    }

    @Override
    public boolean updateTodoStatus(String userId, String todoId, TodoStatusEntity status) {
        return false;
    }

    @Override
    public TodoItem createNewTodo(String userId, String todoId, TodoItem todo) {
        todo.setUserId(userId);
        todo.setId(todoId);
        repo.createOrUpdateTodoItem(todo);

        publisher.publishTodoEvent(todo);

        // TODO: Should leverage putItemWithResponse, maybe.
        return repo.retrieveTodoItem(userId, todoId);
    }
}
