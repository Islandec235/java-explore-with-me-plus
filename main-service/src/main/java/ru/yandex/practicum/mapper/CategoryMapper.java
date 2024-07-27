package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;
import ru.yandex.practicum.model.Category;

@Mapper
public interface CategoryMapper {
    Category toCategory(NewCategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
