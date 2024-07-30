package ru.yandex.practicum.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
}
