package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.event.*;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size);

    EventFullDto createEvent(Long userId, NewEventDto eventDto);

    EventFullDto getEventByIdForUser(Long userId, Long eventId);

    EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto);

    List<ParticipationRequestDto> getRequestByUser(Long userId);

    ParticipationRequestDto createRequestByUser(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId);

    List<ParticipationRequestDto> getRequestForUserAndEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult requestUpdateStatus(Long userId, Long eventId,
                                                       EventRequestStatusUpdateRequest statusUpdateRequest);

    EventFullDto changeEvent(Long eventId, UpdateEventAdminRequest eventDto);
}
