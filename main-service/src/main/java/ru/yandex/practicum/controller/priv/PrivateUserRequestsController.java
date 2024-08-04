package ru.yandex.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.request.ParticipationRequestDto;
import ru.yandex.practicum.service.EventService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateUserRequestsController {

    private final EventService eventService;

    @GetMapping
    public List<ParticipationRequestDto> privateGetRequestByUser(@PathVariable Long userId) {
        log.info("Private get Request by user {}", userId);
        return eventService.getRequestByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto privateCreateRequestByUser(@PathVariable Long userId,
                                                              @RequestParam Long eventId) {
        log.info("Private post Request user {}, event {}", userId, eventId);
        return eventService.createRequestByUser(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto privateCancelRequestByUser(@PathVariable Long userId,
                                                              @PathVariable Long requestId) {
        log.info("Private patch Request cancel user {}, request {}", userId, requestId);
        return eventService.cancelRequestByUser(userId, requestId);
    }

}
