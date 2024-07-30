package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.model.Category;
import ru.yandex.practicum.model.Event;
import ru.yandex.practicum.model.User;

@Mapper(componentModel = "spring")
public interface EventMapper {
//    EventFullDto toEventFullDto(Event event);
//
//    @Mapping(target = "category", ignore = true)
//    @Mapping(target = "initiator", ignore = true)
//    EventShortDto toEventShortDto(Event event);
//
//    @Mapping(target = "category", ignore = true)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", source = "user")
    @Mapping(target = "category", source = "category")
    Event toEvent(NewEventDto newEventDto, Category category, User user);

//    Event toEvent(NewEventDto newEventDto);

//    @Mapping(target = "category", ignore = true)
//    Event toEvent(UpdateUserEventRequest updateUserEvent);
//
//    @Mapping(target = "category", ignore = true)
//    Event toEvent(UpdateEventAdminRequest updateEventAdmin);
}
