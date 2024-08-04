package ru.yandex.practicum.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.dto.event.*;
import ru.yandex.practicum.model.Category;
import ru.yandex.practicum.model.Event;
import ru.yandex.practicum.model.Location;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.related.EventState;

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


    @Named("setCreatedOnNow")
    LocalDateTime setCreatedOnNow() {
        return LocalDateTime.now().withNano(0);
    }

    @Named("setCreateState")
    EventState setCreateState() {
        return EventState.PENDING;
    }
}
