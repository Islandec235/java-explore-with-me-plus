package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.model.RequestStatus;

import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    Set<Long> requestIds;
    @NotNull
    RequestStatus status;
}
