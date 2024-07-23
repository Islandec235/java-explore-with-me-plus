package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.SaveStatsRequestDto;
import ru.yandex.practicum.dto.StatsResponseDto;
import ru.yandex.practicum.client.StatsClient;

import java.util.List;

import static ru.yandex.practicum.related.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.related.Constants.CONTROLLER_STATS_PATH;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    private final StatsClient statClient;

    @GetMapping(CONTROLLER_STATS_PATH)
    public StatsResponseDto getStats(@RequestParam String start,
                                     @RequestParam String end,
                                     @RequestParam List<String> uris,
                                     @RequestParam Boolean unique) {
        log.info("Get stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        return statClient.getStats(start, end, uris, unique);
    }

    @PostMapping(CONTROLLER_HIT_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public StatsResponseDto saveNewStat(@RequestBody SaveStatsRequestDto saveStatsRequestDto) {
        log.info("Post stats {}", saveStatsRequestDto);
        return statClient.saveNewStat(saveStatsRequestDto);
    }

}
