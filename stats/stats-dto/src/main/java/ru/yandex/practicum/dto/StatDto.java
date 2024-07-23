package ru.yandex.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatDto {
    private String app;
    private String uri;
    private Integer hits;
}
