package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.model.Event;

@Mapper(componentModel = "spring")
public abstract class EventMapper {


    public abstract EventFullDto toEventFullDto(Event event);

    public abstract EventShortDto toEventShortDto(Event event);

    @Mapping(target = "id", ignore = true)
    public abstract Event toEvent(NewEventDto newEventDto);

    public abstract Event toEvent(UpdateUserEventRequest updateUserEvent);
}
