package net.choge.myapp.api.service;

import net.choge.myapp.api.entity.TodoItem;
import net.choge.myapp.api.entity.TodoStatusEntity;

import java.util.List;

public interface TodoService {
    List<TodoItem> loadAllTodosForUser(String userId);
    TodoItem loadSingleTodoItem(String userId, String todoId);
    boolean updateTodoStatus(String userId, String todoId, TodoStatusEntity status);

    TodoItem createNewTodo(String userId, String todoId, TodoItem todo);
}
