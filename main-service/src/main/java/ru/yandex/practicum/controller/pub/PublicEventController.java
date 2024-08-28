package ru.yandex.practicum.controller.pub;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.StatsSaveRequestDto;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.related.EventParam;
import ru.yandex.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Tag(name = "Public: События", description = "Публичный API для работы с событиями")
public class PublicEventController {

    private final EventService service;

    @GetMapping
    @Operation(summary = "Получение событий с возможностью фильтрации")
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            HttpServletRequest request) {
        log.info("Получение событий");
        StatsSaveRequestDto statsSaveRequestDto = new StatsSaveRequestDto("emv-main-service",
                request.getServletPath(), request.getRemoteAddr(),
                LocalDateTime.now().withNano(0));
        EventParam param = new EventParam(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);
        return service.getEvents(param, statsSaveRequestDto);
    }

    @GetMapping("/{id}")
    @Transactional
    @Operation(summary = "Получение подробной информации о событии по идентификатору")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("Получение события id = {}", id);
        StatsSaveRequestDto statsSaveRequestDto = new StatsSaveRequestDto("emv-main-service",
                request.getServletPath(), request.getRemoteAddr(),
                LocalDateTime.now().withNano(0));
        return service.getEventById(id, statsSaveRequestDto);
    }
}
