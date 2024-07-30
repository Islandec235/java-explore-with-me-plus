package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.dto.event.*;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.model.*;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final StatsClient statsClient;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    /*@Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findByInitiatorId(userId, PageRequest.of(from / size, size));
        List<EventShortDto> eventsDto = new ArrayList<>();

        for (Event event : events) {
            eventsDto.add(eventMapper.toEventShortDto(event));
        }

        return eventsDto;
    }*/

    @Override
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        User initiator = checkUserInDB(userId);
        Category category = checkCategoryInDB(eventDto.getCategory());
        Event event = eventMapper.toEvent(eventDto, category, initiator);

        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setState(EventState.PENDING);

        event = eventRepository.save(event);

        return null;
    }

    @Override
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        return null;
    }

    @Override
    public List<EventShortDto> getEvents(EventParam param) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long id) {
        return null;
    }

    @Override
    public List<EventFullDto> searchEvents(EventSearchParam param) {
        return null;
    }

    @Override
    public EventFullDto changeEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        return null;
    }

    private User checkUserInDB(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + "не найден"));
    }

    private Category checkCategoryInDB(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + catId + "не найдена"));
    }
/*
    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
        return eventToDto(event);
    }

    @Override
    @Transactional
    public EventFullDto changeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие опубликовано и не может быть изменено");
        }

        if (!eventDto.getEventDate().isAfter(LocalDateTime.now().withNano(0))) {
            throw new IncorrectDateException("Событие не может начинаться раньше чем через 2 часа от текущего момента");
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

        return eventToDto(eventRepository.save(newEvent));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(EventParam param) {
        BooleanExpression predicate = event.isNotNull();
        PageRequest page = PageRequest.of(param.getFrom() / param.getSize(), param.getSize());

        if (param.isText()) {
            predicate = predicate.and(event.annotation.likeIgnoreCase(param.getText()));
        }

        if (param.isCategories()) {
            predicate = predicate.and(event.category.id.in((Number) param.getCategories()));
        }

        if (param.isPaid()) {
            predicate = predicate.and(event.paid.eq(param.getPaid()));
        }

        if (param.isStart() && param.isEnd()) {
            predicate = predicate.and(event.createdOn.between(param.getRangeStart(), param.getRangeEnd()));
        } else if (param.isStart()) {
            predicate = predicate.and(event.createdOn.after(param.getRangeStart()));
        } else if (param.isEnd()) {
            predicate = predicate.and(event.createdOn.before(param.getRangeEnd()));
        }

//        if (param.getOnlyAvailable()) {
//
//        }

        List<Event> events = eventRepository.findAll(predicate, page).toList();
        List<EventShortDto> eventsDto = new ArrayList<>();

        for (Event event : events) {
            eventsDto.add(eventMapper.toEventShortDto(event));

        }

        return eventsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + id + " не найдено"));

        if (event.getState() != EventState.PUBLISHED) {
            throw new NotFoundException("Событие с id = " + id + " недоступно");
        }

        return eventToDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> searchEvents(EventSearchParam param) {
        BooleanExpression predicate = event.isNotNull();
        PageRequest page = PageRequest.of(param.getFrom() / param.getSize(), param.getSize());

        if (param.isUsers()) {
            predicate = predicate.and(event.id.in(param.getUsers()));
        }

        if (param.isStates()) {
            predicate = predicate.and(event.state.in(param.getStates()));
        }

        if (param.isCategories()) {
            predicate = predicate.and(event.category.id.in((Number) param.getCategories()));
        }

        if (param.isStart() && param.isEnd()) {
            predicate = predicate.and(event.createdOn.between(param.getRangeStart(), param.getRangeEnd()));
        } else if (param.isStart()) {
            predicate = predicate.and(event.createdOn.after(param.getRangeStart()));
        } else if (param.isEnd()) {
            predicate = predicate.and(event.createdOn.before(param.getRangeEnd()));
        }

        List<Event> events = eventRepository.findAll(predicate, page).toList();
        List<EventFullDto> eventsDto = new ArrayList<>();

        for (Event event : events) {
            eventsDto.add(eventToDto(event));
        }

        return eventsDto;
    }

    @Override
    @Transactional
    public EventFullDto changeEvent(Long eventId, UpdateEventAdminRequest eventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Событие опубликовано и не может быть изменено");
        }

        if (!eventDto.getEventDate().isAfter(LocalDateTime.now().withNano(0))) {
            throw new IncorrectDateException("Событие не может начинаться раньше чем через 2 часа от текущего момента");
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
            case REJECT_EVENT -> newEvent.setState(EventState.CANCELED);

            case PUBLISH_EVENT -> newEvent.setState(EventState.PENDING);

            default -> newEvent.setState(event.getState());
        }

        return eventToDto(eventRepository.save(newEvent));
    }

    private EventFullDto eventToDto(Event event, boolean isTime) {
        if (isTime) {
            event.setViews(statsClient.getStats(event));
            EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
            eventFullDto.setInitiator(userMapper.toUserShortDto(event.getInitiator()));
            return eventFullDto;
        }
        event.setViews(
                statsClient.getStats(
                        LocalDateTime.now().toString(),
                        LocalDateTime.MAX.toString(),
                        List.of("events/" + event.getId()),
                        false));
        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
        eventFullDto.setInitiator(userMapper.toUserShortDto(event.getInitiator()));
        return eventFullDto;
    }*/
}
