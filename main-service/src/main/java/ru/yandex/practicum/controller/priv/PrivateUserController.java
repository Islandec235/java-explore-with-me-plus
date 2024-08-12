package ru.yandex.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class PrivateUserController {
    private final UserService userService;

    @PostMapping("/subscribe/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subscribe(
            @PathVariable Long userId,
            @PathVariable Long followedId
    ) {
        userService.subscribe(userId, followedId);
    }

    @PostMapping("/unsubscribe/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(
            @PathVariable Long userId,
            @PathVariable Long followedId
    ) {
        userService.unsubscribe(userId, followedId);
    }

    @GetMapping("/followers")
    public List<UserDto> getFollowers(@PathVariable Long userId) {
        return userService.getFollowers(userId);
    }

    @GetMapping("/following")
    public List<UserDto> getFollowing(@PathVariable Long userId) {
        return userService.getFollowing(userId);
    }
}
