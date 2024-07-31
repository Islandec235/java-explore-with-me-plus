package ru.yandex.practicum.dto.compilation;

import lombok.Data;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.model.Event;

import java.util.List;
import java.util.Set;

@Data
public class CompilationDto {
    private Long id;
    private Set<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
