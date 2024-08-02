package ru.yandex.practicum.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.dto.event.*;
import ru.yandex.practicum.model.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class EventMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.toCategoryDto(event.getCategory()))")
    @Mapping(target = "initiator", expression = "java(userMapper.toUserShortDto(event.getInitiator()))")
    public abstract EventFullDto toEventFullDto(Event event);

    @Mapping(target = "category", expression = "java(categoryMapper.toCategoryDto(event.getCategory()))")
    @Mapping(target = "initiator", expression = "java(userMapper.toUserShortDto(event.getInitiator()))")
    public abstract EventShortDto toEventShortDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "initiator", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "createdOn", expression = "java(setCreatedOnNow())")
    @Mapping(target = "state", expression = "java(setCreateState())")
    public abstract Event toEvent(NewEventDto newEventDto, User user, Category category, Location location);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "state", ignore = true)
    public abstract void updateEventFromEventDto(@MappingTarget Event event, UpdateUserEventRequest updateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "state", ignore = true)
    public abstract void updEventForAdminEventDto(@MappingTarget Event event, UpdateEventAdminRequest updateDto);


    /*@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateItemFromItemDto(ItemDto itemDto, @MappingTarget Item item);*/

//    Event toEvent(NewEventDto newEventDto);

//    @Mapping(target = "category", ignore = true)
//    public abstract Event toEvent(UpdateUserEventRequest updateUserEvent); // переделать
//
//    @Mapping(target = "category", ignore = true)
//    Event toEvent(UpdateEventAdminRequest updateEventAdmin);

    @Named("setCreatedOnNow")
    LocalDateTime setCreatedOnNow() {
        return LocalDateTime.now().withNano(0);
    }

    @Named("setCreateState")
    EventState setCreateState() {
        return EventState.PENDING;
    }
}
