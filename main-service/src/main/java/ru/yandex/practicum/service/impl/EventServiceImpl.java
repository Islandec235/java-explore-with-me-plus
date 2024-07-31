package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.dto.event.*;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.mapper.UserRequestMapper;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.*;
import ru.yandex.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final StatsClient statsClient;
    private final UserRepository userRepository;
    private final UserRequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final UserRequestMapper userRequestMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findByInitiatorId(userId, PageRequest.of(from, size));

        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        User initiator = checkUserInDB(userId);
        Category category = checkCategoryInDB(eventDto.getCategory());
        Location location = locationRepository.save(eventDto.getLocation());
        Event event = eventMapper.toEvent(eventDto, initiator, category, location);

        event = eventRepository.save(event);

        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        Event event = checkEventForUserInDB(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        Event event = checkEventForUserInDB(userId, eventId);
        checkEventPublished(event);
        updEventForUserEventDto(eventDto, event);
        eventMapper.updateEventFromEventDto(event, eventDto);

        event = eventRepository.save(event);

        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getRequestByUser(Long userId) {
        checkUserInDataBase(userId);
        List<UserRequest> listRequests = requestRepository.findAllByRequesterId(userId);
        return listRequests.stream()
                .map(userRequestMapper::toPartRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequestByUser(Long userId, Long eventId) {
        User user = checkUserInDataBase(userId);
        Event event = checkEventInDB(eventId);

        verificationRequestToEvent(user, event);
        UserRequest userRequest = userRequestMapper.toUserRequest(user, event);
        userRequest = requestRepository.save(userRequest);

        return userRequestMapper.toPartRequestDto(userRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId) {
        checkUserInDataBase(userId);
        UserRequest userRequest = checkUserRequestInDataBase(requestId);
        userRequest.setStatus(RequestStatus.CANCELED);
        userRequest = requestRepository.save(userRequest);

        return userRequestMapper.toPartRequestDto(userRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequestForUserAndEvent(Long userId, Long eventId) {
        List<UserRequest> userRequest = requestRepository.findAllByRequesterIdAndEventId(userId, eventId);
        return userRequest.stream()
                .map(userRequestMapper::toPartRequestDto)
                .collect(toList());
    }

    @Override
    public EventRequestStatusUpdateResult requestUpdateStatus(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest statusUpdateRequest) {
        List<UserRequest> listRequest = requestRepository.findByIdIn(statusUpdateRequest.getRequestIds());
/*
{
  "requestIds": [
    1,
    2,
    3
  ],
  "status": "CONFIRMED" - подтвержден
}

{"requestIds":[13],"status":"REJECTED"}

{"requestIds":[14],"status":"CONFIRMED"}

const target = pm.response.json()["rejectedRequests"][0];

pm.test("Запрос на участие должен иметь статус PENDING / рассматрив при создании и статус REJECTED/ отклоненный
 после выполнения запроса", function () {
    pm.expect(source.status).equal("PENDING");
    pm.expect(target.status).equal("REJECTED");
});

 */

        List<ParticipationRequestDto> list = listRequest.stream()
                .map(userRequestMapper::toPartRequestDto)
                .toList();

        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult(list, new ArrayList<>());

        return updateResult;
    }

    @Override
    @Transactional
    public EventFullDto changeEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        Event event = checkEventInDB(eventId);
        updEventForAdminEventDto(eventDto, event);
        eventMapper.updEventForAdminEventDto(event, eventDto);

        event = eventRepository.save(event);
        EventFullDto dto = eventMapper.toEventFullDto(event);
        return eventMapper.toEventFullDto(event);
    }

    private void updEventForAdminEventDto(UpdateEventAdminRequest eventDto, Event event) {
        if (eventDto.getStateAction() != null) {
            switch (eventDto.getStateAction()) {
                case REJECT_EVENT -> {
                    checkEventStatePublished(event);
                    event.setState(EventState.CANCELED);
                }

                case PUBLISH_EVENT -> {
                    checkDatePublishAndState(event);
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(EventState.PUBLISHED);
                }
            }
        }

        if (eventDto.getLocation() != null) {
            event.setLocation(locationRepository.save(eventDto.getLocation()));
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(checkCategoryInDB(eventDto.getCategory()));
        }
    }

    private void checkEventStatePublished(Event event) {
        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие можно отклонить, только если оно еще не опубликовано");
        }
    }

    private void checkDatePublishAndState(Event event) {
        if (event.getState() != EventState.PENDING) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        }
        if (LocalDateTime.now().plusHours(1).withNano(0).isAfter(event.getEventDate())) {
            throw new ConflictException("Дата начала изменяемого события должна быть не ранее чем за час " +
                    "от даты публикации");
        }
    }

    private UserRequest checkUserRequestInDataBase(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Значение в базе для UserRequest не найдено: " + id));
    }

    private User checkUserInDataBase(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Значение в базе users не найдено: " + id));
    }

    private Event checkEventInDB(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
    }

    private void verificationRequestToEvent(User user, Event event) {
        long userId = user.getId();
        if (userId == event.getInitiator().getId()) {
            throw new ConflictException("Инициатор события userId " + userId + " не может добавить запрос на участие " +
                    "в своём событии.");
        }
        if (event.getPublishedOn() == null) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии id - " + event.getId());
        }
        if (event.getParticipantLimit() != 0) {
            long countRequest = requestRepository.countByStatusAndEventId(RequestStatus.CONFIRMED, event.getId());
            if (countRequest >= event.getParticipantLimit()) {
                throw new ConflictException("У события достигнут лимит запросов на участие, id - " + event.getId());
            }
        }
    }

    private void checkEventPublished(Event event) {
        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие с id = " + event.getId() + " опубликовано и не может быть изменено");
        }
    }

    private void updEventForUserEventDto(UpdateUserEventRequest eventDto, Event event) {
        if (eventDto.getStateAction() != null) {
            switch (eventDto.getStateAction()) {
                case CANCEL_REVIEW -> event.setState(EventState.CANCELED);

                case SEND_TO_REVIEW -> {
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(EventState.PUBLISHED);
                }
            }
        }

        if (eventDto.getLocation() != null) {
            event.setLocation(locationRepository.save(eventDto.getLocation()));
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(checkCategoryInDB(eventDto.getCategory()));
        }
    }

    private Event checkEventForUserInDB(Long userId, Long eventId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
    }

    private User checkUserInDB(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + "не найден"));
    }

    private Category checkCategoryInDB(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + catId + "не найдена"));
    }
}
