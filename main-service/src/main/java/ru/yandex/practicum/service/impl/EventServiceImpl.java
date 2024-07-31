package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.repository.LocationRepository;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.EventService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final StatsClient statsClient;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

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
        Event event = checkEventForUserInDB(eventId, userId);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        Event event = checkEventForUserInDB(eventId, userId);

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие с id = " + eventId + " опубликовано и не может быть изменено");
        }


        Event newEvent = eventMapper.toEvent(eventDto);
        newEvent.setId(eventId);
        newEvent.setInitiator(event.getInitiator());

        if (eventDto.getCategory() != null) {
            newEvent.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() ->
                            new NotFoundException("Категория с id = " + eventDto.getCategory() + " не найдена")));
        } else {
            newEvent.setCategory(event.getCategory());
        }

        switch (eventDto.getStateAction()) {
            case CANCEL_REVIEW -> newEvent.setState(EventState.CANCELED);

            case SEND_TO_REVIEW -> newEvent.setState(EventState.PENDING);

            default -> newEvent.setState(event.getState());
        }

//        return eventToDto(eventRepository.save(newEvent));
        return null;
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
