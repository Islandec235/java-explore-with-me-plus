package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.EventShortDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.dto.event.UpdateEventAdminRequest;
import ru.yandex.practicum.dto.event.UpdateUserEventRequest;
import ru.yandex.practicum.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "category", ignore = true)
    Event toEvent(NewEventDto newEventDto);

    @Mapping(target = "category", ignore = true)
    Event toEvent(UpdateUserEventRequest updateUserEvent);

    @Mapping(target = "category", ignore = true)
    Event toEvent(UpdateEventAdminRequest updateEventAdmin);
}
