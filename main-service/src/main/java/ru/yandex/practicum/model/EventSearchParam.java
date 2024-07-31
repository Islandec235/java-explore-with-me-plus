package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class EventSearchParam {
    private List<Long> users;
    private List<EventState> states;
    private List<Integer> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Integer from;
    private Integer size;

    public boolean isUsers() {
        return users != null;
    }

    public boolean isStates() {
        return states != null;
    }

    public boolean isCategories() {
        return categories != null;
    }

    public boolean isStart() {
        return rangeStart != null;
    }

    public boolean isEnd() {
        return rangeEnd != null;
    }
}
