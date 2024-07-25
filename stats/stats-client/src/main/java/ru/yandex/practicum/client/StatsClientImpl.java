package ru.yandex.practicum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsResponseHitDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;

import java.util.Arrays;
import java.util.List;

import static ru.yandex.practicum.related.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.related.Constants.CONTROLLER_STATS_PATH;

@Service
@AllArgsConstructor
public class StatsClientImpl implements StatsClient {

    private final WebClient webClient;

    @Override
    public List<StatCountHitsResponseDto> getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique) {
        Object[] listObj = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_STATS_PATH)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(Object[].class).block();

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(listObj)
                .map(object -> mapper.convertValue(object, StatCountHitsResponseDto.class))
                .toList();
    }

    @Override
    public Mono<StatsResponseHitDto> saveNewStat(final StatsSaveRequestDto request) {
        return webClient
                .post()
                .uri(CONTROLLER_HIT_PATH)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(request), StatsResponseHitDto.class)
                .retrieve()
                .bodyToMono(StatsResponseHitDto.class);
    }
}
