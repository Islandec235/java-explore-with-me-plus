package ru.yandex.practicum.controller.priv;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsForUser(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get events userId = {}, from = {}, size = {}", userId, from, size);
        return eventService.getEventsForUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(
            @PathVariable Long userId,
            @RequestBody @Valid NewEventDto eventDto) {
        log.info("Create event {} userId = {}", eventDto, userId);
        return eventService.createEvent(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdForUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Get event userId = {}, eventId = {}", userId, eventId);
        return eventService.getEventByIdForUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto userChangeEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateUserEventRequest eventDto) {
        log.info("Patch User change event {} userId = {}, eventId = {}", eventDto, userId, eventId);
        return eventService.changeEvent(userId, eventId, eventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestForUserAndEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Get request userId = {}, eventId = {}", userId, eventId);
        return eventService.getRequestForUserAndEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult requestUpdateStatus(@PathVariable Long userId,
                                                              @PathVariable Long eventId,
                                                              @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest) {
        log.info("Patch request change update {} ", statusUpdateRequest);
        return eventService.requestUpdateStatus(userId, eventId, statusUpdateRequest);
    }

    @PatchMapping("/{eventId}/requests/")
    public EventRequestStatusUpdateResult requestUpdateStatus_Patch(@PathVariable Long userId,
                                                                    @PathVariable Long eventId,
                                                                    @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest) {
        log.info("Patch request change update {} ", statusUpdateRequest);
        return eventService.requestUpdateStatus(userId, eventId, statusUpdateRequest);
    }
}
