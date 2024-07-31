package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.mapper.UserRequestMapper;
import ru.yandex.practicum.model.Event;
import ru.yandex.practicum.model.RequestStatus;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.model.UserRequest;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.repository.UserRequestRepository;
import ru.yandex.practicum.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserRequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserMapper userMapper;
    private final UserRequestMapper userRequestMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<User> users = (ids == null || ids.isEmpty()) ?
                repository.findAll(PageRequest.of(from, size)).getContent() :
                repository.findByIdIn(ids, PageRequest.of(from, size));

        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest newUser) {
        User user = userMapper.toUser(newUser);
        return userMapper.toUserDto(repository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = checkUserInDataBase(id);
        repository.deleteById(id);
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
            long countRequest = requestRepository.countByStatusAndEventId(RequestStatus.APPROVED, event.getId());
            if (countRequest >= event.getParticipantLimit()) {
                throw new ConflictException("У события достигнут лимит запросов на участие, id - " + event.getId());
            }
        }
    }

    private User checkUserInDataBase(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Значение в базе users не найдено: " + id));
    }

    private UserRequest checkUserRequestInDataBase(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Значение в базе для UserRequest не найдено: " + id));
    }

    private Event checkEventInDB(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
    }
}
