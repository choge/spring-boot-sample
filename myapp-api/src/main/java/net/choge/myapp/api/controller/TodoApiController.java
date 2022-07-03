package net.choge.myapp.api.controller;

import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.dto.TodoItem;
import net.choge.myapp.api.dto.TodoStatus;
import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        TodoItemEntity todo = service.loadSingleTodoItem(userId, id);
        if (Objects.isNull(todo)) {
            return null;
        }
        return new TodoItem(todo.getId(), ZonedDateTime.now(), todo.getContent(), TodoStatus.from(todo.getStatus()));
    }
}
