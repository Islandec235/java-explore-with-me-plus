package ru.yandex.practicum.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.StatCountHitsResponseDto;
import ru.yandex.practicum.dto.StatsResponseHitDto;
import ru.yandex.practicum.dto.StatsSaveRequestDto;
import ru.yandex.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.yandex.practicum.controller.Constants.CONTROLLER_HIT_PATH;
import static ru.yandex.practicum.controller.Constants.CONTROLLER_STATS_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Статистика", description = "API для работы со статистикой")
public class StatsController {

    private final StatsService service;

    @Operation(summary = "Сохранение", description = "Сохраняет информацию о запросе пользователя")
    @PostMapping(CONTROLLER_HIT_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public StatsResponseHitDto saveInfo(@RequestBody StatsSaveRequestDto saveRequestDto) {
        log.info("Запрос на сохранение {}", saveRequestDto);
        return service.saveInfo(saveRequestDto);
    }

    @GetMapping(CONTROLLER_STATS_PATH)
    @Operation(summary = "Вывод статистики", description = "Выводит статистику, удовлетворяющей параметрам")
    public List<StatCountHitsResponseDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false, defaultValue = "") List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Запрос на вывод статистики uris {}", uris);
        return service.getStats(start, end, uris, unique);
    }
}
