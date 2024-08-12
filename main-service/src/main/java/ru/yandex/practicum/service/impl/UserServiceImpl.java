package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

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
        checkUserInDataBase(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void subscribe(Long followerId, Long followedId) {
        User follower = checkUserInDataBase(followerId);
        User followed = checkUserInDataBase(followedId);
        checkTwoUser(follower, followed);
        follower.follow(followed);
        repository.saveAll(List.of(follower, followed));

    }

    @Override
    @Transactional
    public void unsubscribe(Long followerId, Long followedId) {
        User follower = checkUserInDataBase(followerId);
        User followed = checkUserInDataBase(followedId);
        checkTwoUser(follower, followed);
        follower.unfollow(followed);
        repository.saveAll(List.of(follower, followed));
    }

    @Override
    public List<UserDto> getFollowers(Long userId) {
        User user = checkUserInDataBase(userId);
        return toListUserDto(user.getFollowers());
    }

    @Override
    public List<UserDto> getFollowing(Long userId) {
        User user = checkUserInDataBase(userId);
        return toListUserDto(user.getFollowing());
    }

    private List<UserDto> toListUserDto(Set<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            userDtoList.add(userMapper.toUserDto(user));
        }

        return userDtoList;
    }

    private void checkTwoUser(User user, User otherUser) {
        if (user.equals(otherUser)) {
            throw new ConflictException("Пользователи совпадают");
        }
    }

    private User checkUserInDataBase(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Значение в базе users не найдено: " + id));
    }
}
