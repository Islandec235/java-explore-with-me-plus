package ru.yandex.practicum.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.model.Category;
import ru.yandex.practicum.model.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Category category;
    private Integer confirmedRequests = 0;
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    @Future(message = "Должно содержать дату, которая еще не наступила")
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    private Boolean paid = false;
    private Integer participantLimit = 0;
    private Boolean requestModeration = true; // дефолтные значения указываемые нами лучше вынести в маппер, когда маппим из
    // дто в сущность. там же и заполняем их
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    private Long views = 0L;
}
