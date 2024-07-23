package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.HitDto;
import ru.yandex.practicum.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    HitDto toHitDto(Hit hit);

    @Mapping(target = "timestamp", ignore = true)
    Hit toHit(HitDto hitDto);
}
