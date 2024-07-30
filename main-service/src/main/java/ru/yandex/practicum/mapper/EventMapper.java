package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.dto.event.NewEventDto;
import ru.yandex.practicum.model.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class EventMapper {


    public abstract EventFullDto toEventFullDto(Event event);

//    @Mapping(target = "category", ignore = true)
//    @Mapping(target = "initiator", ignore = true)
//    EventShortDto toEventShortDto(Event event);
//
//    @Mapping(target = "category", ignore = true)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "createdOn", expression = "java(setCreatedOnNow())")
    @Mapping(target = "state", expression = "java(setCreateState())")
    public abstract Event toEvent(NewEventDto newEventDto, User user, Category category, Location location);

//    Event toEvent(NewEventDto newEventDto);

//    @Mapping(target = "category", ignore = true)
//    Event toEvent(UpdateUserEventRequest updateUserEvent);
//
//    @Mapping(target = "category", ignore = true)
//    Event toEvent(UpdateEventAdminRequest updateEventAdmin);

    @Named("setCreatedOnNow()")
    LocalDateTime setCreatedOnNow() {
        return LocalDateTime.now().withNano(0);
    }

    @Named("setCreateState()")
    EventState setCreateState() {
        return EventState.PENDING;
    }
}
