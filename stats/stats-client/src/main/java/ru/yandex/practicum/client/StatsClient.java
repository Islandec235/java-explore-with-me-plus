package ru.yandex.practicum.client;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;

import java.util.List;

public interface StatsClient {
    List<StatCountHitsResponseDto> getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique);

    ResponseEntity<Void> saveRestClientNewStat(final StatsSaveRequestDto request);
}
