package ru.yandex.practicum.client;

import ru.yandex.practicum.dto.SaveStatsRequestDto;
import ru.yandex.practicum.dto.StatsResponseDto;

import java.util.List;

public interface StatsClient {
    StatsResponseDto getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique);

    String saveNewStat(SaveStatsRequestDto request);
}
