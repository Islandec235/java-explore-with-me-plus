package ru.yandex.practicum.dto.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserShortDto {
    @NonNull
    private Long id;
    @NonNull
    private String name;
}
