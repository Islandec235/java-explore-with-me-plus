package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.exception.IncorrectDateException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.model.Event;
import ru.yandex.practicum.model.EventSort;
import ru.yandex.practicum.model.EventState;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
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
            log.error("Событие не должно начинаться менее чем через 2 часа от текущего момента eventDate = {}",
                    eventDto.getEventDate());
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

        return mapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto userChangeEvent(Long userId, Long eventId, UpdateUserEventRequest eventDto) {
        return null;
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
}
