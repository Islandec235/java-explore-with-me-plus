package ru.yandex.practicum.dto;

import lombok.Data;
import ru.yandex.practicum.dto.event.EventShortDto;

import java.util.List;

@Data
public class UpdateCompilationRequestDto {
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
