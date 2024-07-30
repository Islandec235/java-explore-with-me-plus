package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.model.EventParam;
import ru.yandex.practicum.model.EventSearchParam;
import ru.yandex.practicum.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size);
    EventFullDto createEvent(Long userId, NewEventDto eventDto);
    EventFullDto getEventByIdForUser(Long userId, Long eventId);
    EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto);
    List<EventShortDto> getEvents(EventParam param);
    EventFullDto getEventById(Long id);
    List<EventFullDto> searchEvents(EventSearchParam param);
    EventFullDto changeEvent(Long eventId, UpdateEventAdminRequest eventDto);
}
