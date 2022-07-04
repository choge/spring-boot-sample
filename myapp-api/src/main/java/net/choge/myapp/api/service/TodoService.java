package net.choge.myapp.api.service;

import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.entity.TodoStatusEntity;

import java.util.List;

public interface TodoService {
    List<TodoItemEntity> loadAllTodosForUser(String userId);
    TodoItemEntity loadSingleTodoItem(String userId, String todoId);
    boolean updateTodoStatus(String userId, String todoId, TodoStatusEntity status);

    TodoItemEntity createNewTodo(String userId, String todoId, TodoItemEntity todo);
}
