package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.model.Stat;

@Mapper(componentModel = "spring")
public abstract class StatsMapper {

    @Mapping(target = "id", ignore = true)
    public abstract StatDto toStatDto(StatsSaveRequestDto saveRequestDto);

    public abstract Stat toStat(StatDto statDto);

    public abstract StatsResponseHitDto toResponseDto(Stat stat);

    public abstract StatCountHitsResponseDto toCountHitsResponseDto(StatCountHitsDto countHitsDto);


}
