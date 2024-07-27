package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;

public interface CategoryService {
    CategoryDto create(NewCategoryDto categoryDto);

    void delete(Integer catId);

    CategoryDto change(Integer catId, NewCategoryDto categoryDto);
}
