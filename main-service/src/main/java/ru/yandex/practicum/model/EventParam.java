package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class EventParam {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private Integer from;
    private Integer size;

    public boolean isText() {
        return text != null;
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

    public boolean isPaid() {
        return paid != null;
    }
}
