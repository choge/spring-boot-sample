package net.choge.myapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class TodoItem {
    private String id;
    private ZonedDateTime due;
    private String content;
    private TodoStatus status;
}
