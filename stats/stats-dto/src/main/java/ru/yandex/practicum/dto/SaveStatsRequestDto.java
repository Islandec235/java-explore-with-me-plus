package ru.yandex.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveStatsRequestDto {
    private String app;
    private String uri;
    private String ip;
    private ZonedDateTime timestamp;
}
