package ru.yandex.practicum.controller.priv;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService service;

    @GetMapping
    public List<EventShortDto> getEventsForUser(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get events userId = {}, from = {}, size = {}", userId, from, size);
        return service.getEventsForUser(userId, from, size);
    }

    @PostMapping
    public EventFullDto createEvent(
            @PathVariable Long userId,
            @RequestBody @Valid NewEventDto eventDto) {
        log.info("Create event {} userId = {}", eventDto, userId);
        return service.createEvent(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdForUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Get event userId = {}, eventId = {}", userId, eventId);
        return service.getEventByIdForUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto userChangeEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody UpdateUserEventRequest eventDto) {
        log.info("User change event {} userId = {}, eventId = {}", eventDto, userId, eventId);
        return service.changeEvent(userId, eventId, eventDto);
    }
}
