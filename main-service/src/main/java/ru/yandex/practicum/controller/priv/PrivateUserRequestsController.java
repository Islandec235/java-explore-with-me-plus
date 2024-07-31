package ru.yandex.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.service.UserService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateUserRequestsController {

    private final UserService userService;

    @GetMapping
    public List<ParticipationRequestDto> privateGetRequestByUser(@PathVariable Long userId) {
        log.info("Private get Request by user {}", userId);
        return userService.getRequestByUser(userId);
    }

    @PostMapping
    public ParticipationRequestDto privateCreateRequestByUser(@PathVariable Long userId,
                                                             @RequestParam Long eventId) {
        log.info("Private post Request user {}, event {}", userId, eventId);
        return userService.createRequestByUser(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto privateCancelRequestByUser(@PathVariable Long userId,
                                                             @PathVariable Long requestId) {
        log.info("Private patch Request cancel user {}, request {}", userId, requestId);
        return userService.cancelRequestByUser(userId, requestId);
    }

}
