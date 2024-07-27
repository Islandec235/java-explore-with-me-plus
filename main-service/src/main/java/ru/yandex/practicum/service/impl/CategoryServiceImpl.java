package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
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
            log.error("Категория с id = {} не найдена", catId);
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
}
