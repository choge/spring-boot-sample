package net.choge.myapp.api.controller;

import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.dto.TodoItemDTO;
import net.choge.myapp.api.dto.TodoStatus;
import net.choge.myapp.api.entity.TodoItem;
import net.choge.myapp.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TodoItemDTO getTodo(@PathVariable String id) {
        String userId = extractUserId();
        TodoItem todo = service.loadSingleTodoItem(userId, id);
        if (Objects.isNull(todo)) {
            return null;
        }
        return fromTodoItemEntity(todo);
    }

    @PostMapping("/{id}")
    public TodoItemDTO createTodo(@PathVariable String id, @RequestBody TodoItemDTO todo) {
        String userId = extractUserId();
        TodoItem todoItem = new TodoItem(userId, id, todo.getContent(), todo.getDue().toInstant().toEpochMilli(), todo.getStatus().name());
        TodoItem created = service.createNewTodo(userId, id, todoItem);
        return fromTodoItemEntity(created);
    }

    private String extractUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private TodoItemDTO fromTodoItemEntity(TodoItem todoItem) {
        return new TodoItemDTO(todoItem.getId(),
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(todoItem.getDueAsUnixtime()), ZoneId.of("UTC")),
            // Is this that messy...??? Anyway should externalize as a util class or something.
            todoItem.getContent(), TodoStatus.from(todoItem.getStatus()));
    }
}
