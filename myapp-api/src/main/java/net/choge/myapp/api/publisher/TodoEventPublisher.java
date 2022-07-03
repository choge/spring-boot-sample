package net.choge.myapp.api.publisher;

import net.choge.myapp.api.entity.TodoItemEntity;

public interface TodoEventPublisher {
    public boolean publishTodoEvent(TodoItemEntity todo);
}
