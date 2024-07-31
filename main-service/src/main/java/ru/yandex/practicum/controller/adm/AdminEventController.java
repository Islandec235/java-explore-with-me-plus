package ru.yandex.practicum.controller.adm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventSearchParam;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService service;

    @GetMapping
    public List<EventFullDto> searchEvents(@Valid EventSearchParam searchParam) {
        log.info("Admin search events {}", searchParam);
        return null;
    }

    @PatchMapping("/{eventId}")
    @Transactional
    public EventFullDto adminChangeEvent(
            @PathVariable Long eventId,
            @RequestBody UpdateEventAdminRequest eventDto) {
        log.info("Admin change event {} eventId = {}", eventDto, eventId);
        return service.changeEvent(eventId, eventDto);
    }
}
