package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.IncorrectDateException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.model.Event;
import ru.yandex.practicum.model.EventSort;
import ru.yandex.practicum.model.EventState;
import ru.yandex.practicum.model.Location;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserMapper userMapper;
    private final EventMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findByUserId(userId, PageRequest.of(from / size, size));
        List<EventShortDto> eventsDto = new ArrayList<>();

        for (Event event : events) {
            eventsDto.add(mapper.toEventShortDto(event));
        }

        return eventsDto;
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        if (!eventDto.getEventDate().isAfter(LocalDateTime.now().withNano(0).plusHours(2))) {
            throw new IncorrectDateException("Событие не должно начинаться менее чем через 2 часа от текущего момента");
        }

        Event event = mapper.toEvent(eventDto);
        event.setCategory(categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Категория с id = " + eventDto.getCategory() + "не найдена")));
        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + "не найден")));
        event.setLat(event.getLat());
        event.setLon(event.getLon());
        event.setState(EventState.PENDING);


        return eventToDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
        return eventToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto userChangeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие опубликовано и не может быть изменено");
        }

        if (!eventDto.getEventDate().isAfter(LocalDateTime.now().withNano(0))) {
            throw new IncorrectDateException("Событие не может начинаться раньше чем через 2 часа от текущего момента");
        }

        Event newEvent = mapper.toEvent(eventDto);
        newEvent.setId(eventId);
        newEvent.setInitiator(event.getInitiator());

        if (eventDto.getCategory() != null) {
            newEvent.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() ->
                            new NotFoundException("Категория с id = " + eventDto.getCategory() + " не найдена")));
        } else {
            newEvent.setCategory(event.getCategory());
        }

        if (eventDto.getLocation() != null) {
            newEvent.setLat(eventDto.getLocation().getLat());
            newEvent.setLon(eventDto.getLocation().getLon());
        }

        switch (eventDto.getStateAction()) {
            case REJECT_EVENT -> newEvent.setState(EventState.CANCELED);

            case PUBLISH_EVENT ->  newEvent.setState(EventState.PUBLISHED);

            default -> newEvent.setState(event.getState());
        }

        return eventToDto(eventRepository.save(newEvent));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(
            String text,
            List<Integer> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> searchEvents(
            List<Integer> users,
            List<EventState> states,
            List<Integer> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto adminChangeEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        return null;
    }

    private EventFullDto eventToDto(Event event) {
        EventFullDto eventFullDto = mapper.toEventFullDto(event);
        eventFullDto.setInitiator(userMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(new Location(event.getLat(), event.getLon()));
        return eventFullDto;
    }
}
