package ru.yandex.practicum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;

import java.util.Arrays;
import java.util.List;

import static ru.yandex.practicum.related.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.related.Constants.CONTROLLER_STATS_PATH;

@Service
@AllArgsConstructor
public class StatsClientImpl implements StatsClient {

    private final RestClient restClient;

    @Override
    public List<StatCountHitsResponseDto> getStats(final String startTime, final String endTime, final List<String> uris, final Boolean unique) {
        Object[] listObj = restClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(CONTROLLER_STATS_PATH)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .body(Object[].class);

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(listObj)
                .map(object -> mapper.convertValue(object, StatCountHitsResponseDto.class))
                .toList();
    }

    @Override
    public ResponseEntity<Void> saveRestClientNewStat(final StatsSaveRequestDto request) {
        return restClient.post()
                .uri(CONTROLLER_HIT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
