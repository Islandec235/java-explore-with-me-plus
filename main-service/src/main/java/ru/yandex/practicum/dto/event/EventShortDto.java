package ru.yandex.practicum.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    @NonNull
    private String annotation;
    @NonNull
    private CategoryDto category;
    private Integer confirmedRequests;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
    private UserShortDto initiator;
    @NonNull
    private Boolean paid;
    @NonNull
    private String title;
    private Long views;
}
