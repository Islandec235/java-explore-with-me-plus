package ru.yandex.practicum.controller.priv;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Private: Запросы на участие",
        description = "Закрытый API для работы с запросами текущего пользователя на участие в событиях")
public class PrivateUserRequestsController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Получение информации о заявках текущего пользователя на участие в чужих событиях")
    public List<ParticipationRequestDto> privateGetRequestByUser(@PathVariable Long userId) {
        log.info("Private get Request by user {}", userId);
        return eventService.getRequestByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление запроса от текущего пользователя на участие в событии")
    public ParticipationRequestDto privateCreateRequestByUser(@PathVariable Long userId,
                                                              @RequestParam Long eventId) {
        log.info("Private post Request user {}, event {}", userId, eventId);
        return eventService.createRequestByUser(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @Operation(summary = "Отмена запроса на участие в событии")
    public ParticipationRequestDto privateCancelRequestByUser(@PathVariable Long userId,
                                                              @PathVariable Long requestId) {
        log.info("Private patch Request cancel user {}, request {}", userId, requestId);
        return eventService.cancelRequestByUser(userId, requestId);
    }

}
