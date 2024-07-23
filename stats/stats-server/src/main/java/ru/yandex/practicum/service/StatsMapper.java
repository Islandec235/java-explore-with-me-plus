package ru.yandex.practicum.service;

import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.SaveStatsRequestDto;
import ru.yandex.practicum.dto.StatsResponseDto;
import ru.yandex.practicum.model.Hit;
import ru.yandex.practicum.model.Stat;

@Mapper(componentModel = "spring")
public abstract class StatsMapper {

    public abstract Hit toHit (SaveStatsRequestDto saveStatsRequestDto);

    public abstract StatsResponseDto toResponse (Stat stat);

}
