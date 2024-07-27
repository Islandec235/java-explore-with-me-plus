package ru.yandex.practicum.dto.category;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class NewCategoryDto {
    @NonNull
    @Size(min = 1, max = 50)
    private String name;
}
