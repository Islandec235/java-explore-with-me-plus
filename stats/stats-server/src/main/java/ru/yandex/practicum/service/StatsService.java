package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsResponseHitDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    StatsResponseHitDto saveInfo(StatsSaveRequestDto hit);

    List<StatCountHitsResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
