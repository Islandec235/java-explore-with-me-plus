package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;
import ru.yandex.practicum.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toCategory(NewCategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
