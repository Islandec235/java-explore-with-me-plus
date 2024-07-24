package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.StatDto;
import ru.yandex.practicum.model.Stat;

@Mapper(componentModel = "spring")
public interface StatMapper {
    Stat toStat(StatDto statDto);

    StatDto toStatDto(Stat stat);
}
