package ru.yandex.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}
