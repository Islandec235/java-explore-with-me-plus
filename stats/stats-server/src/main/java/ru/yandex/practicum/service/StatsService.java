package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Hit;
import ru.yandex.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    Hit saveInfo(Hit hit);

    List<Stat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
