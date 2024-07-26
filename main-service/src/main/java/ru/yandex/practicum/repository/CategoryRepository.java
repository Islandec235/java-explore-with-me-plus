package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
