package net.choge.myapp.api.controller;

import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.dto.TodoItem;
import net.choge.myapp.api.dto.TodoStatus;
import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/todos")
@Log4j2
public class TodoApiController {

    private final TodoService service;

    public TodoApiController(@Autowired TodoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public TodoItem getTodo(@PathVariable String id) {
        String userId = extractUserId();
        TodoItemEntity todo = service.loadSingleTodoItem(userId, id);
        if (Objects.isNull(todo)) {
            return null;
        }
        return fromTodoItemEntity(todo);
    }

    @PostMapping("{id}")
    public TodoItem createTodo(@PathVariable String id, @RequestBody TodoItem todo) {
        String userId = extractUserId();
        TodoItemEntity todoItemEntity = new TodoItemEntity(userId, id, todo.getContent(), todo.getDue().toInstant().toEpochMilli(), todo.getStatus().name());
        TodoItemEntity created = service.createNewTodo(userId, id, todoItemEntity);
        return fromTodoItemEntity(created);
    }

    private String extractUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private TodoItem fromTodoItemEntity(TodoItemEntity todoItemEntity) {
        return new TodoItem(todoItemEntity.getId(),
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(todoItemEntity.getDueAsUnixtime()), ZoneId.of("UTC")),
            // Is this that messy...??? Anyway should externalize as a util class or something.
            todoItemEntity.getContent(), TodoStatus.from(todoItemEntity.getStatus()));
    }
}
