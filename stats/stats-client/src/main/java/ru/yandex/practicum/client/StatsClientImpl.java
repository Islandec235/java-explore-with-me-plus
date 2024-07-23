package ru.yandex.practicum.client;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.dto.SaveStatsRequestDto;
import ru.yandex.practicum.dto.StatsResponseDto;

import java.util.List;

import static ru.yandex.practicum.related.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.related.Constants.CONTROLLER_STATS_PATH;

@Service
@AllArgsConstructor
public class StatsClientImpl implements StatsClient {

    private final WebClient webClient;

    @Override
    public StatsResponseDto getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_STATS_PATH)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(StatsResponseDto.class)
                .block();
    }

    @Override
    public StatsResponseDto saveNewStat(final SaveStatsRequestDto request) {
        return webClient
                .post()
                .uri(CONTROLLER_HIT_PATH)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(StatsResponseDto.class)
                .block();
    }
}
