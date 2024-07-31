package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto create(NewUserRequest newUser);

    void delete(Long id);

    List<ParticipationRequestDto> getRequestByUser(Long userId);

    ParticipationRequestDto createRequestByUser(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId);
}
