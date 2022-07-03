package net.choge.myapp.api.repository;

import net.choge.myapp.api.entity.TodoItemEntity;

import java.util.List;

public interface TodoRepository {

    public TodoItemEntity retrieveTodoItem(String userId, String id);
    public List<TodoItemEntity> loadTodoItems(String userId);
    public boolean createOrUpdateTodoItem(TodoItemEntity item);
}
