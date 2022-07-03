package net.choge.myapp.api.dto;

import java.util.Objects;

public enum TodoStatus {
    NOT_STARTED,
    IN_PROGRESS,
    PENDING,
    DONE;

    public static TodoStatus from(String status) {
        if (Objects.equals(status, "NOT_STARTED")) {
            return NOT_STARTED;
        } else if (Objects.equals(status, "IN_PROGRESS")) {
            return IN_PROGRESS;
        } else {
            return DONE;
        }
    }
}
