package net.choge.myapp.api.repository;

import net.choge.myapp.api.entity.TodoItem;

import java.util.List;

public interface TodoRepository {

    TodoItem retrieveTodoItem(String userId, String id);
    List<TodoItem> loadTodoItems(String userId);
    boolean createOrUpdateTodoItem(TodoItem item);
}
