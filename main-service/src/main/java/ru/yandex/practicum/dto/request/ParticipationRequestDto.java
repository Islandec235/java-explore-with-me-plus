package ru.yandex.practicum.dto.request;

import lombok.Data;
import ru.yandex.practicum.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private RequestStatus status;
    private LocalDateTime created;
}
