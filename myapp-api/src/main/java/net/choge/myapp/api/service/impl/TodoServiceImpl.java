package net.choge.myapp.api.service.impl;

import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.entity.TodoStatusEntity;
import net.choge.myapp.api.repository.TodoRepository;
import net.choge.myapp.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository repo;

    public TodoServiceImpl(@Autowired TodoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TodoItemEntity> loadAllTodosForUser(String userId) {
        return null;
    }

    @Override
    public TodoItemEntity loadSingleTodoItem(String userId, String todoId) {
        return repo.retrieveTodoItem(userId, todoId);
    }

    @Override
    public boolean updateTodoStatus(String userId, String todoId, TodoStatusEntity status) {
        return false;
    }
}
