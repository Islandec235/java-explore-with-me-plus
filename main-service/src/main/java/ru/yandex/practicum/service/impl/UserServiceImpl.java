package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.UserMapper;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.repository.UserRepository;
import ru.yandex.practicum.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<User> users = new ArrayList<>();
        List<UserDto> usersDto = new ArrayList<>();

        if (ids == null) {
            users = repository.findAll(PageRequest.of(from / size, size)).toList();
        } else {
//            users = repository.findAllById(ids, PageRequest.of(from / size, size));
        }

        for (User user : users) {
            usersDto.add(mapper.toUserDto(user));
        }

        return usersDto;
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest newUser) {
        User user = mapper.toUser(newUser);
        return mapper.toUserDto(repository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }

        repository.deleteById(id);
    }
}
