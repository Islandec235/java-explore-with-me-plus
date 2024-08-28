package ru.yandex.practicum.controller.adm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.user.NewUserRequest;
import ru.yandex.practicum.dto.user.UserDto;
import ru.yandex.practicum.service.UserService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Tag(name = "Admin: Пользователи", description = "API для работы с пользователями")
public class AdminUserController {
    private final UserService service;

    @GetMapping
    @Operation(summary = "Получение информации о пользователях",
            description = "Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки)")
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получение пользователей ids = {}, from = {}, size = {}", ids, from, size);
        return service.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового пользователя")
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUser) {
        log.info("Создание пользователя {}", newUser);
        return service.create(newUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    public void deleteUser(@PathVariable Long id) {
        log.info("Удаление пользователя id = {}", id);
        service.delete(id);
    }
}