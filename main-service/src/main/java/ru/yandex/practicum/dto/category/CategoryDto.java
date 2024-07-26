package ru.yandex.practicum.dto.category;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryDto {
    private Long id;
    @NonNull
    @Size(min = 1, max = 50)
    private String name;
}
