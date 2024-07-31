package ru.yandex.practicum.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.model.RequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    List<Long> requestIds;
    @NotNull
    RequestStatus status;
}
