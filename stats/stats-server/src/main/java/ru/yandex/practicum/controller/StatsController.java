package ru.yandex.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.SaveStatsRequestDto;
import ru.yandex.practicum.dto.StatsResponseDto;
import ru.yandex.practicum.service.StatsMapper;
import ru.yandex.practicum.model.Hit;
import ru.yandex.practicum.model.Stat;
import ru.yandex.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;
    private final StatsMapper statsMapper;

    @PostMapping("/hit")
    public /*dto*/Hit saveInfo(@RequestBody /*dto*/SaveStatsRequestDto dto) {
        log.info("Запрос на сохранение {}", dto);
        Hit hit = statsMapper.toHit(dto);
        return service.saveInfo(hit);
    }

    @GetMapping("/stats")
    public /*dto*/List<StatsResponseDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Запрос на вывод статистики");

        return service.getStats(start, end, uris, unique).stream()
                .map(statsMapper::toResponse)
                .collect(Collectors.toList());
    }
}
