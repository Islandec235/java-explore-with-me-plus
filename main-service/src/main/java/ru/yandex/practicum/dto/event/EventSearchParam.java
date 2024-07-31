package ru.yandex.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventSearchParam {
    private List<Long> users;
    private List<EventState> states;
    private List<Integer> categories;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;
    @NotNull
    @Min(0)
    private Integer from = 0;
    @NotNull
    @Min(1)
    private Integer size = 10;
}
