package ru.yandex.practicum.controller.priv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.user.UserDto;
import ru.yandex.practicum.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
@Tag(name = "Private: Пользователи", description = "Закрытый API для работы с пользователями")
public class PrivateUserController {
    private final UserService userService;

    @PostMapping("/subscribe/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Подписка на пользователя")
    public void subscribe(
            @PathVariable Long userId,
            @PathVariable Long followedId
    ) {
        log.info("Запрос подписки userId = {} на followedId = {}", userId, followedId);
        userService.subscribe(userId, followedId);
    }

    @PostMapping("/unsubscribe/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Отписка от пользователя")
    public void unsubscribe(
            @PathVariable Long userId,
            @PathVariable Long followedId
    ) {
        log.info("Запрос отписки userId = {} на followedId = {}", userId, followedId);
        userService.unsubscribe(userId, followedId);
    }

    @GetMapping("/followers")
    @Operation(summary = "Получение списка подписчиков")
    public List<UserDto> getFollowers(@PathVariable Long userId) {
        log.info("Запрос на получение подписчиков user = {}", userId);
        return userService.getFollowers(userId);
    }

    @GetMapping("/following")
    @Operation(summary = "Получение списка отслеживаемых пользователей")
    public List<UserDto> getFollowing(@PathVariable Long userId) {
        log.info("Запрос на получение подписок user = {}", userId);
        return userService.getFollowing(userId);
    }
}
