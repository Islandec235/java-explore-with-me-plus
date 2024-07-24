package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsResponseHitDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;

import java.util.List;

import static ru.yandex.practicum.related.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.related.Constants.CONTROLLER_STATS_PATH;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsClient statClient;

    @GetMapping(CONTROLLER_STATS_PATH)
    public List<StatCountHitsResponseDto> getStats(@RequestParam String start,
                                                   @RequestParam String end,
                                                   @RequestParam List<String> uris,
                                                   @RequestParam Boolean unique) {
        log.info("Get stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        return statClient.getStats(start, end, uris, unique);
    }

    @PostMapping(CONTROLLER_HIT_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StatsResponseHitDto> saveNewStat(@RequestBody StatsSaveRequestDto saveStatsRequestDto) {
        log.info("Post stats {}", saveStatsRequestDto);
        return statClient.saveNewStat(saveStatsRequestDto);
    }

}
