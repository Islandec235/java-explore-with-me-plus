package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.category.CategoryDto;
import ru.yandex.practicum.dto.category.NewCategoryDto;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.CategoryMapper;
import ru.yandex.practicum.model.Category;
import ru.yandex.practicum.repository.CategoryRepository;
import ru.yandex.practicum.repository.EventRepository;
import ru.yandex.practicum.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        Category category = mapper.toCategory(categoryDto);
        return mapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Integer catId) {
        if (eventRepository.findByCategoryId(catId).isEmpty()) {
            if (categoryRepository.findById(catId).isPresent()) {
                categoryRepository.deleteById(catId);
            } else {
                throw new NotFoundException("Категория с id = " + catId + " не найдена");
            }
        } else {
            throw new ConflictException("Категория с id = " + catId + " связана с событиями");
        }
    }

    @Override
    @Transactional
    public CategoryDto change(Integer catId, NewCategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + catId + " не найдена"));
        category.setName(categoryDto.getName());
        return mapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(from / size, size)).toList();
        List<CategoryDto> categoriesDto = new ArrayList<>();

        for (Category category : categories) {
            categoriesDto.add(mapper.toCategoryDto(category));
        }

        return categoriesDto;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Integer catId) {
        return mapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + catId + " не найдена")));
    }
}
