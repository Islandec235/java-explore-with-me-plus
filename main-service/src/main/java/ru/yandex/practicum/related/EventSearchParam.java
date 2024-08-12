package ru.yandex.practicum.related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventSearchParam {
    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Integer from;
    private Integer size;

    public EventSearchParam(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

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
