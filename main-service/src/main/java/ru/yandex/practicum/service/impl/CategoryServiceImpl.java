package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.CategoryMapper;
import ru.yandex.practicum.model.Category;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        Category category = mapper.toCategory(categoryDto);
        return mapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        checkCategoryInDataBase(catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto change(Long catId, NewCategoryDto categoryDto) {
        Category category = checkCategoryInDataBase(catId);
        category.setName(categoryDto.getName());
        return mapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(from, size)).getContent();

        return categories.stream()
                .map(mapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long catId) {
        return mapper.toCategoryDto(checkCategoryInDataBase(catId));
    }

    private Category checkCategoryInDataBase(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + id + " не найдена"));
    }
}
