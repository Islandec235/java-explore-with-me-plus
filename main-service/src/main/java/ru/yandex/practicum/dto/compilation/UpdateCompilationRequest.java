package ru.yandex.practicum.dto.compilation;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UpdateCompilationRequest {
    private Set<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50)
    private String title;
}
