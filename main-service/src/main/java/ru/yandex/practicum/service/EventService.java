package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.model.EventSort;
import ru.yandex.practicum.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size);
    EventFullDto createEvent(Long userId, NewEventDto eventDto);
    EventFullDto getEventByIdForUser(Long userId, Long eventId);
    EventFullDto userChangeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto);
    List<EventShortDto> getEvents(
            String text,
            List<Integer> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size);
    EventFullDto getEventById(Long id);
    List<EventFullDto> searchEvents(
            List<Integer> users,
            List<EventState> states,
            List<Integer> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size);
    EventFullDto adminChangeEvent(Long eventId, UpdateEventAdminRequest eventDto);
}
