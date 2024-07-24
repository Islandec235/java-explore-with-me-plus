package ru.yandex.practicum.client;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;
import ru.yandex.practicum.dto.StatsResponseHitDto;

import java.util.List;

public interface StatsClient {
    List<StatCountHitsResponseDto> getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique);

    Mono<StatsResponseHitDto> saveNewStat(final StatsSaveRequestDto request);
}
