package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventFullDto eventFullDto);

    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "lat", ignore = true)
    @Mapping(target = "lon", ignore = true)
    @Mapping(target = "participantLimit", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    Event toEvent(NewEventDto newEventDto);
}
