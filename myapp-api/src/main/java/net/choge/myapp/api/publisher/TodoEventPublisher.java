package net.choge.myapp.api.publisher;

import net.choge.myapp.api.entity.TodoItem;

public interface TodoEventPublisher {
    boolean publishTodoEvent(TodoItem todo);
}
