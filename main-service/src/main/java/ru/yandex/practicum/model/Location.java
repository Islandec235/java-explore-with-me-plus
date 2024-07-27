package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private Float lat;
    private Float lon;
}
