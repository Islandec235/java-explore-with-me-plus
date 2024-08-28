package ru.yandex.practicum.controller.adm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;
import ru.yandex.practicum.service.CategoryService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Tag(name = "Admin: Категории", description = "API для работы с категориями")
public class AdminCategoryController {

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание категории", description = "Добавление новой категории, если её имя уникальное")
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto categoryDto) {
        log.info("Создание категории {}", categoryDto);
        return service.create(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление категории", description = "Удаляет категорию, если она не связана с событиями")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Удаление категории categoryId = {}", catId);
        service.delete(catId);
    }

    @PatchMapping("/{catId}")
    @Operation(summary = "Изменение категории", description = "Изменяет категорию, если её имя уникальное")
    public CategoryDto changeCategory(
            @PathVariable Long catId,
            @RequestBody @Valid NewCategoryDto categoryDto) {
        return service.change(catId, categoryDto);
    }
}
