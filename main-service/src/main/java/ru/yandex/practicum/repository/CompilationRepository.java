package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Compilation;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    Optional<Compilation> getCompilationById(Long compId);

    Page<Compilation> findAllByPinned(Boolean pinned, PageRequest pageRequest);
}
