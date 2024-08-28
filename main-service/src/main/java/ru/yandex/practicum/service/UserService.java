package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto create(NewUserRequest newUser);

    void delete(Long id);

    void subscribe(Long followerId, Long followedId);

    void unsubscribe(Long followerId, Long followedId);

    List<UserDto> getFollowers(Long userId);

    List<UserDto> getFollowing(Long userId);
}
