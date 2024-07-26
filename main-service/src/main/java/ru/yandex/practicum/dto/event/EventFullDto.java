package ru.yandex.practicum.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.user.UserShortDto;
import ru.yandex.practicum.model.EventState;
import ru.yandex.practicum.model.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    @NonNull
    private String annotation;
    @NonNull
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
    private UserShortDto initiator;
    @NonNull
    private Location location;
    @NonNull
    private Boolean paid;
    private Integer participantLimit = 0;
    private LocalDateTime publishedOn;
    private Boolean requestModeration = true;
    private EventState state;
    @NonNull
    private String title;
    private Long views;
}
